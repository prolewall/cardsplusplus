package com.cardsplusplus.cards.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.cardsplusplus.cards.App.Companion.deviceHeight
import com.cardsplusplus.cards.App.Companion.deviceWidth
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.*

class GameBoard(context: Context, val gameOptions: GameOptions)
    : SurfaceView(context), SurfaceHolder.Callback, SensorEventListener  {

    val players = mutableListOf<Player>()
    val currPlayerField: CurrentPlayerField
    val playerControlField: PlayerControlField
    val playingField: PlayingField

    var currPlayerIndex: Int = 0

    var showChangePlayerScreen = true

    val touchableSurfaces: MutableList<TouchableSurface> = mutableListOf()

    private lateinit var gameThread: GameThread

    private var sensorManager: SensorManager

    private var gyroscopeSensor: Sensor? = null

    private var wasTilt = false

    private val tiltEventTimeout = 800 //in miliseconds
    private val TILT_THRESHOLD = 1.8
    private var lastTimestamp = 0

    private val cardChangeSpeed: Long = 200
    private val playerChangeSpeed: Long = 300

    init {
        this.isFocusable = true
        this.holder.addCallback(this)

        currPlayerField = CurrentPlayerField()
        playerControlField = PlayerControlField()
        playingField = PlayingField()

        touchableSurfaces.add(playingField)
        touchableSurfaces.add(currPlayerField)
        touchableSurfaces.add(playerControlField)

        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    fun update() {
        for(player in players){
            player.update()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val clickPos = Point(event.x.toInt(), event.y.toInt())
        var gameEvent = GameEvent.NONE

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(showChangePlayerScreen) {

                    val card = players[currPlayerIndex].changeScreenCard
                    animateHorizontalMove(card, card.bounds.left, deviceWidth, playerChangeSpeed)

                    this.postDelayed({ showChangePlayerScreen = false}, playerChangeSpeed)
                }
                else{
                    for(surface: TouchableSurface in touchableSurfaces) {
                        if(surface.wasClicked(clickPos)){
                            gameEvent = surface.getEvent(clickPos)
                        }
                    }

                    handleGameEvent(gameEvent)
                }
            }

        }
        return true
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        this.gameThread.running = false
        this.gameThread.join()

        sensorManager.unregisterListener(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        positionSurfaces()
        initSurfaces()

        this.gameThread = GameThread(this, holder)
        this.gameThread.running = true
        this.gameThread.start()

        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawPaint(Paint().apply{color = context.resources.getColor(R.color.background_color)})

        playingField.draw(canvas)
        currPlayerField.draw(canvas)
        playerControlField.draw(canvas)

        if(showChangePlayerScreen) {
            drawChangePlayerScreen(canvas)
        }
    }

    private fun handleGameEvent(event: GameEvent) {
        val currPlayer = players[currPlayerIndex]

        when(event) {
            GameEvent.DRAW_CARD -> {
                try{
                    val hand = currPlayer.hand

                    currPlayer.drawCardFrom(playingField.drawPile)
                    animateVerticalMove(hand.getSelectedCard(), hand.rect.top, hand.handTop, cardChangeSpeed)
                    currPlayer.hand.selectedCardIndex = currPlayer.hand.count() - 1
                    animateDiagonalMove(hand.getSelectedCard(), playingField.drawPile.pos,
                            Point(hand.getSelectedCard().pos.x, hand.rect.top), 250)
                }
                catch(e: IndexOutOfBoundsException) {
                    playingField.drawPile.putMultipleCardsOnTop(playingField.throwPile.takeAllCards())
                    playingField.drawPile.shuffle()
                }

            }
            GameEvent.PLAY_CARD -> {
                try{
                    val hand = currPlayer.hand
                    val card = hand.getSelectedCard()

                    animateDiagonalMove(card, card.pos, playingField.throwPile.pos, 250)

                    animateVerticalMove(hand.getRightCard(), hand.handTop, hand.rect.top, cardChangeSpeed)

                    this.postDelayed({currPlayer.playSelectedCardTo(playingField.throwPile)
                        }, 250)
                    if(currPlayer.hand.count() == 0) {
                        currPlayer.hand.selectedCardIndex = -1
                    }
                }
                catch(e: IndexOutOfBoundsException) { }
            }
            GameEvent.SELECT_LEFT -> {
                val hand = currPlayer.hand

                try{
                    animateVerticalMove(hand.getSelectedCard(), hand.rect.top, hand.handTop, cardChangeSpeed)
                    currPlayer.hand.shiftSelectedLeft()
                    animateVerticalMove(hand.getSelectedCard(), hand.handTop, hand.rect.top, cardChangeSpeed)
                }
                catch(e: IndexOutOfBoundsException) { }
            }
            GameEvent.SELECT_RIGHT -> {
                val hand = currPlayer.hand

                try{
                    animateVerticalMove(hand.getSelectedCard(), hand.rect.top, hand.handTop, cardChangeSpeed)
                    currPlayer.hand.shiftSelectedRight()
                    animateVerticalMove(hand.getSelectedCard(), hand.handTop, hand.rect.top, cardChangeSpeed)
                }
                catch(e: IndexOutOfBoundsException) { }
            }
            GameEvent.NEXT_PLAYER -> {
                currPlayerIndex = incrementIndex(currPlayerIndex, players.count())
                changePlayer(currPlayerIndex)
            }
            GameEvent.PREV_PLAYER -> {
                currPlayerIndex = decrementIndex(currPlayerIndex, players.count())
                changePlayer(currPlayerIndex)
            }

        }
    }

    private fun positionSurfaces() {
        val height = (deviceHeight * 0.35).toInt()
        val controlPanelHeight = (deviceHeight * 0.15).toInt()

        playingField.rect = Rect(0, 0, deviceWidth, deviceHeight/2)
        currPlayerField.rect = Rect(0, deviceHeight - height - controlPanelHeight,
                deviceWidth, deviceHeight - controlPanelHeight)
        playerControlField.rect = Rect(0, deviceHeight - controlPanelHeight,
                deviceWidth, deviceHeight)
    }

    private fun initSurfaces() {
        playingField.drawPile.createFullDeck()
        playingField.drawPile.shuffle()

        for(i: Int in 1 until gameOptions.initialCardsPerPlayer) {

            val player = Player("Player $i", CardSymbol.values()[i-1])
            player.hand.addMultipleCards(playingField.drawPile.takeMultipleCards(gameOptions.initialCardsPerPlayer))
            players.add(player)
        }
        changePlayer(0)
    }

    private fun changePlayer(playerIndex: Int) {
        val card = players[playerIndex].changeScreenCard

        card.width = (0.9 * deviceWidth).toInt()
        val pos = Point(getCenter(0, card.width,deviceWidth),
                getCenter(0, card.height, deviceHeight))
        card.bounds = Rect(pos.x, pos.y, pos.x + card.width, pos.y + card.height)

        animateHorizontalMove(card, deviceWidth, pos.x, 300)

        showChangePlayerScreen = true
        this.postDelayed({
            currPlayerField.changePlayer(players[playerIndex])
            playerControlField.player = players[playerIndex]
            }, 300)

    }

    private fun drawChangePlayerScreen(canvas: Canvas) {

        players[currPlayerIndex].changeScreenCard.draw(canvas)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            var timestamp = (event.timestamp * 0.000001).toInt()

            val values = event.values.clone()

            val rotX = values[0]
            val rotY = values[1]
            val rotZ = values[2]


            if(!showChangePlayerScreen) {
                if(timestamp - lastTimestamp >= tiltEventTimeout){
                    wasTilt = false
                    var event = GameEvent.NONE
                    if(rotZ >= TILT_THRESHOLD) {
                        event = GameEvent.SELECT_LEFT
                        lastTimestamp = timestamp
                        wasTilt = true
                    }
                    if(rotZ <= -TILT_THRESHOLD) {
                        event = GameEvent.SELECT_RIGHT
                        lastTimestamp = timestamp
                        wasTilt = true
                    }
                    if(rotX <= -TILT_THRESHOLD) {
                        event = GameEvent.PLAY_CARD
                        lastTimestamp = timestamp
                        wasTilt = true
                    }
                    if(rotX >= TILT_THRESHOLD) {
                        event = GameEvent.DRAW_CARD
                        lastTimestamp = timestamp
                        wasTilt = true
                    }
                    if(rotY >= TILT_THRESHOLD) {
                        event = GameEvent.PREV_PLAYER
                        lastTimestamp = timestamp
                        wasTilt = true
                    }
                    if(rotY <= -TILT_THRESHOLD) {
                        event = GameEvent.NEXT_PLAYER
                        lastTimestamp = timestamp
                        wasTilt = true
                    }

                    handleGameEvent(event)
                }
            }



        }
    }

}