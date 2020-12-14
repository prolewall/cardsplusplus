package com.cardsplusplus.cards.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import androidx.annotation.RequiresApi

fun drawDrawable(canvas: Canvas, drawable: Drawable?, posX: Int, posY: Int, width: Int, height: Int) {
    drawable?.bounds = Rect(posX, posY, posX + width, posY + height)
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