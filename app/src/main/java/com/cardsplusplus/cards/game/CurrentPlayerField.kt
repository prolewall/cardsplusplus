package com.cardsplusplus.cards.game

import android.graphics.*
import android.text.TextPaint
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.drawDrawable
import com.cardsplusplus.cards.utils.drawText
import com.cardsplusplus.cards.utils.makeRect

class CurrentPlayerField: TouchableSurface {
    override var rect = Rect(0,0,0,0)
    override var touchEvent: GameEvent = GameEvent.NONE

    lateinit var player: Player

    fun changePlayer(player: Player) {
        val distFromBorder = 0.1*rect.width()
        player.hand.rect = Rect((rect.left + distFromBorder).toInt(),
            (rect.top + 0.1*rect.height()).toInt(),
            (rect.right - distFromBorder).toInt(),
            context.resources.displayMetrics.heightPixels)
    }

    fun draw(canvas: Canvas, player: Player) {

        canvas.drawRect(rect, Paint().apply { color = Color.BLUE })

        player.hand.draw(canvas, true)
    }

    override fun getEvent(pos: Point): GameEvent {
        val fieldWidth = (0.3*rect.width()).toInt()
        val leftField = Rect(rect.left, rect.top, fieldWidth, rect.bottom)
        val rightField = Rect(rect.right - fieldWidth, rect.top, rect.right, rect.bottom)

        var event = GameEvent.NONE

        if(leftField.contains(pos.x, pos.y)) {
            event = GameEvent.SELECT_LEFT
        }
        if(rightField.contains(pos.x, pos.y)){
            event = GameEvent.SELECT_RIGHT
        }
        return event
    }

    companion object{
        val PlayerNamePaint: Paint = TextPaint().apply {
            textAlign = Paint.Align.CENTER
            color = Color.BLACK
            textSize = 20f
        }
    }
}