package com.cardsplusplus.cards.game

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.*

class GameBoard(context: Context, val gameOptions: GameOptions)
    : SurfaceView(context), SurfaceHolder.Callback {

    val players = mutableListOf<Player>()
    val currPlayerField: CurrentPlayerField
    val playerControlField: PlayerControlField
    val playingField: PlayingField

    var currPlayerIndex: Int = 0

    var showChangePlayerScreen = true

    private val touchableSurfaces: MutableList<TouchableSurface> = mutableListOf()

    private lateinit var gameThread: GameThread

    init {
        this.isFocusable = true
        this.holder.addCallback(this)

        currPlayerField = CurrentPlayerField()
        playerControlField = PlayerControlField()
        playingField = PlayingField()

        touchableSurfaces.add(playingField)
        touchableSurfaces.add(currPlayerField)
        touchableSurfaces.add(playerControlField)
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
                    showChangePlayerScreen = false
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
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        positionSurfaces()
        initSurfaces()

        this.gameThread = GameThread(this, holder)
        this.gameThread.running = true
        this.gameThread.start()
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
                    currPlayer.drawCardFrom(playingField.drawPile)
                    currPlayer.hand.selectedCardIndex = currPlayer.hand.count() - 1
                }
                catch(e: IndexOutOfBoundsException) {
                    playingField.drawPile.putMultipleCardsOnTop(playingField.throwPile.takeAllCards())
                    playingField.drawPile.shuffle()
                }

            }
            GameEvent.PLAY_CARD -> {
                try{
                    currPlayer.playSelectedCardTo(playingField.throwPile)
                    if(currPlayer.hand.count() == 0) {
                        currPlayer.hand.selectedCardIndex = -1
                    }
                }
                catch(e: IndexOutOfBoundsException) {

                }
            }
            GameEvent.SELECT_LEFT -> {
                currPlayer.hand.shiftSelectedLeft()
            }
            GameEvent.SELECT_RIGHT -> {
                currPlayer.hand.shiftSelectedRight()
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
        val deviceHeight = context.resources.displayMetrics.heightPixels
        val deviceWidth = context.resources.displayMetrics.widthPixels

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
        currPlayerField.changePlayer(players[playerIndex])
        playerControlField.player = players[playerIndex]
        showChangePlayerScreen = true
    }

    private fun drawChangePlayerScreen(canvas: Canvas) {
        val deviceWidth = context.resources.displayMetrics.widthPixels
        val deviceHeight = context.resources.displayMetrics.heightPixels
        val card = players[currPlayerIndex].changeScreenCard

        card.width = (0.9 * context.resources.displayMetrics.widthPixels).toInt()
        card.draw(canvas, Point(getCenter(0, card.width,deviceWidth),
                getCenter(0, card.height, deviceHeight)))

        val color = if(card.symbol == CardSymbol.DIAMONDS || card.symbol == CardSymbol.HEARTS) {
            Color.BLACK
        } else {
            context.resources.getColor(R.color.red_card_color)
        }
        val distFromSide = (0.65*deviceWidth).toFloat()
        val distFromTop = (0.2*deviceHeight).toFloat()
        drawText(canvas, players[currPlayerIndex].name,
                distFromSide, distFromTop, 100f, 0f, color, cardFont)

        drawText(canvas, players[currPlayerIndex].name,
                deviceWidth - distFromSide, deviceHeight - distFromTop, 100f, 180f, color, cardFont)
    }

}