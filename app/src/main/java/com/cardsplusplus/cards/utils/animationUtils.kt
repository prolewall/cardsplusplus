package com.cardsplusplus.cards.utils

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Point
import android.graphics.drawable.Drawable

fun animateHorizontalMove(drawable: Drawable, from: Int, to: Int, dur: Long) {

    ValueAnimator.ofInt(from, to).apply {
        duration = dur
        addUpdateListener {  animation ->
            val curr = animation.animatedValue as Int
            drawable.bounds.offsetTo(curr, drawable.bounds.top)
        }
        start()
    }

}

fun animateVerticalMove(drawable: Drawable, from: Int, to: Int, dur: Long) {
    ValueAnimator.ofInt(from, to).apply {
        duration = dur
        addUpdateListener {  animation ->
            val curr = animation.animatedValue as Int
            drawable.bounds.offsetTo(drawable.bounds.left, curr)
        }
        start()
    }

}

fun animateDiagonalMove(drawable: Drawable, fromPos: Point, toPos: Point, dur: Long) {

    var propertyX = PropertyValuesHolder.ofInt("PROPERTY_X", fromPos.x, toPos.x)
    var propertyY = PropertyValuesHolder.ofInt("PROPERTY_Y", fromPos.y, toPos.y)
    ValueAnimator().apply {
        duration = dur
        setValues(propertyX, propertyY)
        addUpdateListener {  animation ->
            val x = animation.getAnimatedValue("PROPERTY_X") as Int
            val y = animation.getAnimatedValue("PROPERTY_Y") as Int
            drawable.bounds.offsetTo(x,y)
        }
        start()
    }
}