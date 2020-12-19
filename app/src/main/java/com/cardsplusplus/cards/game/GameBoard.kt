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

class GameBoard(context: Context, val gameOptions: GameOptions)
    : GLSurfaceView(context), SurfaceHolder.Callback {

    val players = mutableListOf<Player>()
    val drawPile: DeckOfCards = DeckOfCards(false)
    val throwPile: DeckOfCards = DeckOfCards(true)
    val currPlayerField: CurrentPlayerField

    var currPlayerIndex: Int = 0

    private lateinit var gameThread: GameThread

    init {
        this.isFocusable = true
        this.holder.addCallback(this)

        currPlayerField = CurrentPlayerField()
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

        when(event.action) {
            MotionEvent.ACTION_BUTTON_PRESS -> {

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

        drawPile.pos = Point(120, 120)
        throwPile.pos = Point(500, 120)

        val height = (deviceHeight * 0.4).toInt()

        currPlayerField.rect = Rect(0, deviceHeight - height, deviceWidth, deviceHeight)
    }

    private fun initSurfaces() {
        drawPile.createFullDeck()
        drawPile.shuffle()

        throwPile.putCardOnTop(drawPile.takeCard())


        for(i: Int in 1 until gameOptions.initialCardsPerPlayer) {
            val player = Player("Player $i")
            player.hand.addMultipleCards(drawPile.takeMultipleCards(gameOptions.initialCardsPerPlayer))
            players.add(player)
        }

        currPlayerField.changePlayer(players[0])
    }

}