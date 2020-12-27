package com.cardsplusplus.cards.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable


fun drawDrawable(canvas: Canvas, img: Drawable?, angle: Float = 0f) {
    canvas.save()
    canvas.rotate(angle, img?.bounds!!.exactCenterX(), img?.bounds!!.exactCenterY())
    img.draw(canvas)

    canvas.restore()
}

fun drawDrawable(canvas: Canvas, img: Drawable?, rect: Rect, angle: Float = 0f){
    img?.bounds = rect
    drawDrawable(canvas, img, angle)
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
        drawDrawable(canvas, drawable, makeRect(pos, width, height), angle)
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