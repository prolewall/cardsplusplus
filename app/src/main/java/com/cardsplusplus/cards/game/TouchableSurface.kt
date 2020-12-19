package com.cardsplusplus.cards.game

import android.graphics.Point
import android.graphics.Rect

interface TouchableSurface {
    var rect: Rect

    fun isPointWithin(point: Point): Boolean {
        return rect.contains(point.x, point.y)
    }


}