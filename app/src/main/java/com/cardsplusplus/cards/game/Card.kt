package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cardsplusplus.cards.App
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.drawDrawable
import com.cardsplusplus.cards.utils.drawText
import com.cardsplusplus.cards.utils.makeRect

abstract class Card: Drawable() {
    var width: Int
        set(w: Int) {
            val h = (w * Card.HEIGHT_RATIO).toInt()
            bounds = Rect(bounds.left, bounds.top, bounds.left + w, bounds.top + h)
        }
        get() = this.bounds.width()

    val height: Int
        get() = bounds.height()

    val pos: Point
        get() = Point(this.bounds.left, this.bounds.top)

    val textSize: Float
        get() = (width * 0.15).toFloat()

    var angle: Float = 0f

    var isFront: Boolean = true

    init {
        width = (Card.WIDTH_PERCENTAGE * App.context.resources.displayMetrics.widthPixels).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (Card.WIDTH_PERCENTAGE * App.context.resources.displayMetrics.widthPixels).toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return (width * Card.HEIGHT_RATIO).toInt()
    }

    override fun draw(canvas: Canvas) {
        //draw blank card
        if(!isFront) {
            cardBack?.bounds = this.bounds
            drawDrawable(canvas, cardBack, angle)
        }
        else{
            cardDrawable?.bounds = this.bounds
            drawDrawable(canvas, cardDrawable, angle)

            val symbolSize = (width * 0.1).toInt()
            val symbolDistanceFromSide = (width * 0.05).toInt()
            val symbolDistanceFromTop = (height * 0.15).toInt()
            // top left symbol
            drawDrawable(canvas, symbolDrawable, makeRect(
                    Point(pos.x + symbolDistanceFromSide, pos.y + symbolDistanceFromTop),
                    symbolSize, symbolSize))
            // bottom right symbol rotated
            drawDrawable(canvas, symbolDrawable, makeRect(
                    Point(pos.x + width - symbolSize - symbolDistanceFromSide,
                            pos.y + height - symbolSize - symbolDistanceFromTop),
                    symbolSize, symbolSize), 180f)
            // top left text
            drawText(canvas, figureText,
                    (pos.x + symbolDistanceFromSide).toFloat(),
                    (pos.y + symbolDistanceFromTop - 0.02 * height).toFloat(),
                    textSize, 0f, cardColor, Card.cardFont)
            // bottom right text rotated
            drawText(canvas, figureText,
                    (pos.x + width - symbolDistanceFromSide).toFloat(),
                    (pos.y + height - symbolDistanceFromTop + 0.02 * height).toFloat(),
                    textSize, 180f, cardColor, Card.cardFont)

            val figDistanceFromSide = (0.17 * width).toInt()
            val figDistanceFromTop = (0.15 * width).toInt()
            drawFigure(canvas, Point(pos.x + figDistanceFromSide, pos.y + figDistanceFromTop),
                    width - 2 * figDistanceFromSide, height - 2 * figDistanceFromSide)

        }

    }

    abstract fun drawFigure(canvas: Canvas, pos: Point, width: Int, height: Int)

    abstract val symbolDrawable: Drawable?

    abstract val figureText: String

    abstract val cardColor: Int

    abstract val cardDrawable: Drawable?

    abstract val cardBack: Drawable?

    companion object {
        const val WIDTH_PERCENTAGE = 0.23

        const val HEIGHT_RATIO = 100f / 72f

        val cardFont = TextPaint().apply {
            typeface = ResourcesCompat.getFont(context, R.font.cards_font)
        }

        val cardDrawables = mapOf<CardSymbol, Drawable>(
                CardSymbol.CLUBS to context.resources.getDrawable(R.drawable.cards_black_card_front_blank, null),
                CardSymbol.SPADES to context.resources.getDrawable(R.drawable.cards_black_card_front_blank, null),
                CardSymbol.HEARTS to context.resources.getDrawable(R.drawable.cards_red_card_front_blank, null),
                CardSymbol.DIAMONDS to context.resources.getDrawable(R.drawable.cards_red_card_front_blank, null)
        )

        val cardBack: Drawable = context.resources.getDrawable(R.drawable.cards_red_card_back, null)

        val redCardColor = ContextCompat.getColor(context, R.color.red_card_color)

        val blackCardColor = ContextCompat.getColor(context, R.color.black)
    }
}