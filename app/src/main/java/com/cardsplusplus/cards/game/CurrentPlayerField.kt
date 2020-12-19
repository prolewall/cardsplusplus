package com.cardsplusplus.cards.game

import android.graphics.*
import android.text.TextPaint
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.drawDrawable
import com.cardsplusplus.cards.utils.drawText
import com.cardsplusplus.cards.utils.makeRect

class CurrentPlayerField {
    var rect = Rect(0,0,0,0)
    lateinit var player: Player

    fun changePlayer(player: Player) {
        player.hand.rect = Rect((rect.left + 0.05*rect.width()).toInt(),
            (rect.top + 0.1*rect.height()).toInt(),
            (rect.right - 0.05*rect.width()).toInt(),
            context.resources.displayMetrics.heightPixels)
    }

    fun draw(canvas: Canvas, player: Player) {

        canvas.drawRect(rect, Paint().apply { color = Color.BLUE })

        player.hand.draw(canvas, true)


    }

    companion object{
        val PlayerNamePaint: Paint = TextPaint().apply {
            textAlign = Paint.Align.CENTER
            color = Color.BLACK
            textSize = 20f
        }
    }
}