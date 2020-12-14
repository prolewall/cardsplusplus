package com.cardsplusplus.cards.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import androidx.annotation.RequiresApi

fun drawDrawable(canvas: Canvas, drawable: Drawable?, pos: Point, width: Int, height: Int) {
    drawable?.bounds = Rect(pos.x, pos.y, pos.x + width, pos.y + height)
    drawable?.draw(canvas)
}

fun drawText(canvas: Canvas, text: String, x: Float, y: Float,
             size: Float, angle: Float, color: Int, style: Paint){
    style.textSize = size
    style.color = color

    canvas.save()
    canvas.rotate(angle, x, y)
    canvas.drawText(text, x, y, style)

    canvas.restore()
}

fun drawMultipleDrawables(canvas: Canvas, drawable: Drawable?,
                          width: Int, height: Int, positions: List<Point>) {
    for(pos in positions) {
        drawDrawable(canvas, drawable, pos, width, height)
    }
}

fun getCenter(a: Int, imgSize: Int, totalSize: Int): Int {
    return (a + totalSize/2 - imgSize/2)
}

fun getBorder(a: Int, imgSize: Int, totalSize: Int): Int {
    return a + totalSize - imgSize
}