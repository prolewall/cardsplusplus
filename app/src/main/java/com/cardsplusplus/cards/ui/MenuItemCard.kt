package com.cardsplusplus.cards.ui

import android.graphics.*
import android.graphics.drawable.Drawable
import com.cardsplusplus.cards.game.Card
import com.cardsplusplus.cards.utils.drawDrawable
import com.cardsplusplus.cards.utils.drawRectText
import com.cardsplusplus.cards.utils.drawText
import com.cardsplusplus.cards.utils.getCenter

class MenuItemCard(override val symbolDrawable: Drawable?, override val figureText: String,
                   override val cardDrawable: Drawable?, override val cardColor: Int,
                   val secondaryText: String, val secondaryColor: Int
): Card() {
    override fun drawFigure(canvas: Canvas, pos: Point, width: Int, height: Int) {
        drawCenterSymbol(canvas, pos, width, height, (0.8 * width).toInt())
    }


    private fun drawCenterSymbol(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerX = getCenter(pos.x, size, width)
        val centerY = getCenter(pos.y, size, height)

        drawDrawable(canvas, symbolDrawable, Point(centerX, centerY), size, size)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawText(canvas, secondaryText, bounds.exactCenterX(),
            (bounds.top + 0.15*height).toFloat(),100f, 0f,
            secondaryColor, Card.cardFont, true)

        drawText(canvas, secondaryText, bounds.exactCenterX(),
            (bounds.bottom - 0.15*height).toFloat(),100f, 180f,
            secondaryColor, Card.cardFont, true)
    }

    override fun setAlpha(alpha: Int) {
        this.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        this.colorFilter = colorFilter
    }
}