package com.cardsplusplus.cards.game

import android.graphics.*
import android.text.TextPaint
import androidx.core.content.res.ResourcesCompat
import com.cardsplusplus.cards.App
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.drawRectText

class PlayerControlField: TouchableSurface {
    override var rect: Rect = Rect(0,0,0,0)

    lateinit var player: Player

    val sideWidthPercent = 0.25

    val leftRect: Rect
        get() = Rect(rect.left, rect.top, (rect.left + sideWidthPercent*rect.width()).toInt(), rect.bottom)

    val centerRect: Rect
        get() = Rect((rect.left + sideWidthPercent*rect.width()).toInt(), rect.top,
                (rect.right - sideWidthPercent*rect.width()).toInt(), rect.bottom)

    val rightRect: Rect
        get() = Rect((rect.right - sideWidthPercent*rect.width()).toInt(), rect.top, rect.right, rect.bottom)


    override fun getEvent(pos: Point): GameEvent {
        var event = GameEvent.NONE

        if(leftRect.contains(pos.x, pos.y)) {
            event = GameEvent.PREV_PLAYER
        }
        if(rightRect.contains(pos.x, pos.y)){
            event = GameEvent.NEXT_PLAYER
        }
        return event
    }

    fun draw(canvas: Canvas) {
        val textSize = 80f

        canvas.drawRect(leftRect, Paint().apply{color = Color.GRAY})
        canvas.drawRect(centerRect, Paint().apply { color = Color.TRANSPARENT })
        canvas.drawRect(rightRect, Paint().apply { color = Color.GRAY })

        drawRectText(canvas, player.name, centerRect, textSize, Color.BLACK, cardFont)
        drawRectText(canvas, "NEXT", rightRect, textSize,
                context.resources.getColor(R.color.red_card_color), cardFont)
        drawRectText(canvas, "PREV", leftRect, textSize,
                context.resources.getColor(R.color.red_card_color), cardFont)
    }

}