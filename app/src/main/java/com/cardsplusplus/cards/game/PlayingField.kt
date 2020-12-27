package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect

class PlayingField: TouchableSurface {
    private var innerRect: Rect = Rect(0,0,0,0)
    override var rect: Rect
        set(newRect) {
            innerRect = newRect

            drawPile.rect.offsetTo((rect.centerX() - 0.2*rect.width() - drawPile.rect.width()/2).toInt(),
                    rect.centerY() - drawPile.rect.height()/2)
            throwPile.rect.offsetTo((rect.centerX() + 0.2*rect.width() - drawPile.rect.width()/2).toInt(),
                    rect.centerY() - drawPile.rect.height()/2)
        }
        get() = innerRect

    val drawPile: DeckOfCards = DeckOfCards(false)
    val throwPile: DeckOfCards = DeckOfCards(true)

    override fun getEvent(pos: Point): GameEvent {
        var event = GameEvent.NONE

        if(drawPile.rect.contains(pos.x, pos.y)) {
            event = GameEvent.DRAW_CARD
        }
        if(throwPile.rect.contains(pos.x, pos.y)) {
            event = GameEvent.PLAY_CARD
        }

        return event
    }

    fun draw(canvas: Canvas) {
        drawPile.draw(canvas)
        throwPile.draw(canvas)
    }
}