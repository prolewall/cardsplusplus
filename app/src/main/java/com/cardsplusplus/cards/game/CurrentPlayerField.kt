package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R

class CurrentPlayerField: TouchableSurface {
    override var rect = Rect(0,0,0,0)


    lateinit var player: Player

    fun changePlayer(player: Player) {
        val distFromBorder = 0.18*rect.width()
        this.player = player
        player.hand.rect = Rect((rect.left + distFromBorder).toInt(),
            (rect.top + 0.1*rect.height()).toInt(),
            (rect.right - distFromBorder).toInt(),
            rect.bottom)
    }

    fun draw(canvas: Canvas) {

        canvas.drawRect(rect, Paint().apply { color = context.resources.getColor(R.color.player_field_color) })

        player.hand.draw(canvas)
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

}