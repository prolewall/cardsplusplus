package com.cardsplusplus.cards.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import androidx.annotation.RequiresApi

fun drawDrawable(canvas: Canvas, drawable: Drawable?, pos: Point, width: Int, height: Int, angle: Float = 0f) {
    drawable?.bounds = Rect(pos.x, pos.y, pos.x + width, pos.y + height)

    canvas.save()
    canvas.rotate(angle, (pos.x + 0.5*width).toFloat(), (pos.y + 0.5*height).toFloat())
    drawable?.draw(canvas)

    canvas.restore()
}

fun drawDrawable(canvas: Canvas, drawable: Drawable?, rect: Rect, angle: Float = 0f) {
    drawable?.bounds = rect

    canvas.save()
    canvas.rotate(angle, rect.exactCenterX(), rect.exactCenterY())
    drawable?.draw(canvas)

    canvas.restore()
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

fun makeRect(pos: Point, width: Int, height: Int): Rect {
    return Rect(pos.x, pos.y, pos.x + width, pos.y + height)
}