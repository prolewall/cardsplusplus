package com.cardsplusplus.cards.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import com.cardsplusplus.cards.R

class GameBoard(context: Context, gameOptions: GameOptions)
    : SurfaceView(context), SurfaceHolder.Callback {

    val players = mutableListOf<Player>()
    val drawPile: DeckOfCards = DeckOfCards(false)
    val throwPile: DeckOfCards = DeckOfCards(true)

    private lateinit var gameThread: GameThread

    init {
        this.isFocusable = true

        this.holder.addCallback(this)
    }

    fun update() {
        for(player in players){
            player.update()
        }
        drawPile.update()
        throwPile.update()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        this.gameThread.running = false
        this.gameThread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        drawPile.putCardOnTop(Card(CardSymbol.HEARTS, CardFigure.KING))

        this.gameThread = GameThread(this, holder)
        this.gameThread.running = true
        this.gameThread.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawPile.draw(canvas)
    }
}