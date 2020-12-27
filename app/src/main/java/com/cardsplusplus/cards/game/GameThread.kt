package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(private val gameBoard: GameBoard, private val surfaceHolder: SurfaceHolder): Thread() {
    var running = false

    override fun run(){
        while(running) {
            var canvas: Canvas? = null
            try {
                canvas = this.surfaceHolder.lockCanvas()

                synchronized(canvas) {
                    this.gameBoard.update()
                    this.gameBoard.draw(canvas)
                }
            } catch (e: Exception){

            } finally {
                if(canvas != null){
                    this.surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }

            try{
                sleep(10)
            } catch(e: InterruptedException) {

            }
        }
    }
}