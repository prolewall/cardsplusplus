package com.cardsplusplus.cards.game

import android.graphics.Point
import android.graphics.Rect
import android.util.Log

interface TouchableSurface {
    var rect: Rect

    fun wasClicked(point: Point): Boolean {
        return rect.contains(point.x, point.y)
    }

    fun getEvent(pos: Point): GameEvent
}