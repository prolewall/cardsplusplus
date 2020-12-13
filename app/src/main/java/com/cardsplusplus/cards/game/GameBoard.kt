package com.cardsplusplus.cards.game

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameBoard(context: Context, gameOptions: GameOptions)
    : SurfaceView(context), SurfaceHolder.Callback {

    val players = List(gameOptions.nrOfPlayers) {}
    lateinit var deck: DeckOfCards

    init {

    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

    }
}