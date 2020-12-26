package com.cardsplusplus.cards.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import android.util.Log
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
             size: Float, angle: Float, color: Int, paint: Paint, isCentered: Boolean = false){
    val style = Paint(paint)
    style.textSize = size
    style.color = color

    if(isCentered)
        style.textAlign = Paint.Align.CENTER

    canvas.save()
    canvas.rotate(angle, x, y)
    canvas.drawText(text, x, y, style)

    canvas.restore()
}

fun drawRectText(canvas: Canvas, text: String, rect: Rect, size: Float, color: Int, paint: Paint) {
    val style = Paint(paint)
    style.textSize = size
    style.color = color
    style.textAlign = Paint.Align.CENTER

    canvas.drawText(text, rect.exactCenterX(), rect.exactCenterY(), style)
}

fun drawMultipleDrawables(canvas: Canvas, drawable: Drawable?,
                          width: Int, height: Int, positions: List<Point>, angle: Float = 0f) {
    for(pos in positions) {
        canvas.save()

        canvas.rotate(angle, (pos.x + 0.5*width).toFloat(), (pos.y + 0.5*height).toFloat())
        drawDrawable(canvas, drawable, pos, width, height)
        canvas.restore()
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