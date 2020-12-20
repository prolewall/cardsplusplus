package com.cardsplusplus.cards.game

import android.content.Context
import android.graphics.*
import android.opengl.GLSurfaceView
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import com.cardsplusplus.cards.R
import kotlin.IndexOutOfBoundsException

class GameBoard(context: Context, val gameOptions: GameOptions)
    : SurfaceView(context), SurfaceHolder.Callback {

    val players = mutableListOf<Player>()
    val drawPile: DeckOfCards = DeckOfCards(false)
    val throwPile: DeckOfCards = DeckOfCards(true)
    val currPlayerField: CurrentPlayerField

    var currPlayerIndex: Int = 0

    private val touchableSurfaces: MutableList<TouchableSurface> = mutableListOf()

    private lateinit var gameThread: GameThread

    init {
        this.isFocusable = true
        this.holder.addCallback(this)

        currPlayerField = CurrentPlayerField()

        touchableSurfaces.add(drawPile)
        touchableSurfaces.add(throwPile)
        touchableSurfaces.add(currPlayerField)
    }

    fun update() {
        for(player in players){
            player.update()
        }
        drawPile.update()
        throwPile.update()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val clickPos = Point(event.x.toInt(), event.y.toInt())
        var gameEvent = GameEvent.NONE

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                for(surface: TouchableSurface in touchableSurfaces) {
                    if(surface.wasClicked(clickPos)){
                        gameEvent = surface.getEvent(clickPos)
                    }
                }

                when(gameEvent) {
                    GameEvent.DRAW_CARD -> {
                        try{
                            players[currPlayerIndex].drawCardFrom(drawPile)
                        }
                        catch(e: IndexOutOfBoundsException) {
                            drawPile.putMultipleCardsOnTop(throwPile.takeAllCards())
                            drawPile.shuffle()
                        }

                    }
                    GameEvent.PLAY_CARD -> {
                        try{
                            players[currPlayerIndex].playSelectedCardTo(throwPile)
                        }
                        catch(e: IndexOutOfBoundsException) {

                        }
                    }
                    GameEvent.SELECT_LEFT -> {
                        players[currPlayerIndex].hand.shiftSelectedLeft()
                    }
                    GameEvent.SELECT_RIGHT -> {
                        players[currPlayerIndex].hand.shiftSelectedRight()
                    }
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

        drawPile.draw(canvas)
        throwPile.draw(canvas)
        currPlayerField.draw(canvas, players[currPlayerIndex])
    }

    private fun positionSurfaces() {
        val deviceHeight = context.resources.displayMetrics.heightPixels
        val deviceWidth = context.resources.displayMetrics.widthPixels

        drawPile.rect.offsetTo(120, 120)
        throwPile.rect.offsetTo(500, 120)

        val height = (deviceHeight * 0.4).toInt()

        currPlayerField.rect = Rect(0, deviceHeight - height, deviceWidth, deviceHeight)
    }

    private fun initSurfaces() {
        drawPile.touchEvent = GameEvent.DRAW_CARD
        drawPile.createFullDeck()
        drawPile.shuffle()

        throwPile.touchEvent = GameEvent.PLAY_CARD


        for(i: Int in 1 until gameOptions.initialCardsPerPlayer) {
            val player = Player("Player $i")
            player.hand.addMultipleCards(drawPile.takeMultipleCards(gameOptions.initialCardsPerPlayer))
            players.add(player)
        }

        currPlayerField.changePlayer(players[0])
    }

}