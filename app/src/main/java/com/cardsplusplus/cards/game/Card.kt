package com.cardsplusplus.cards.game


import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextPaint
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cardsplusplus.cards.App
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.drawDrawable
import com.cardsplusplus.cards.utils.drawText

enum class CardSymbol {
    DIAMONDS, HEARTS, SPADES, CLUBS
}

enum class CardFigure {
    ACE, N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING
}

class Card(val symbol: CardSymbol, val figure: CardFigure) : Drawable() {
    private var width: Int = 0
    private var height: Int = 0
    var pos: Point = Point(0,0)
    var scale: Float = 3f
    var textSize: Float = 0f

    init {
        width = (0.2 * context.resources.displayMetrics.xdpi * context.resources.displayMetrics.density).toInt()
        height = (width * (142f/102f)).toInt()
        textSize = (width * 0.15).toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas) {

        val width = (scale * width).toInt()
        val height = (scale * height).toInt()
        val textSize = scale * textSize
        //draw blank card
        drawDrawable(canvas, cardDrawables[symbol], pos.x, pos.y, width, height)

        val symbolSize = (width*0.1).toInt()
        val symbolDistanceFromSide = (width*0.05).toInt()
        val symbolDistanceFromTop = (height*0.15).toInt()
        // top left symbol
        drawDrawable(canvas, symbolDrawables[symbol],
                pos.x + symbolDistanceFromSide,
                pos.y + symbolDistanceFromTop,
                symbolSize, symbolSize)
        // bottom right symbol
        drawDrawable(canvas, symbolRotatedDrawables[symbol],
                pos.x + width - symbolSize - symbolDistanceFromSide,
                pos.y + height - symbolSize - symbolDistanceFromTop,
                symbolSize, symbolSize)
        // top left text
        drawText(canvas, figureToText(figure),
                (pos.x + symbolDistanceFromSide).toFloat(),
                (pos.y + symbolDistanceFromTop - 0.02 * height).toFloat(),
                textSize, 0f, symbolToColor(symbol), cardFont)
        // bottom right text
        drawText(canvas, figureToText(figure),
                (pos.x + width - symbolDistanceFromSide).toFloat(),
                (pos.y + height - symbolDistanceFromTop + 0.02 * height).toFloat(),
                textSize, 180f, symbolToColor(symbol), cardFont)

    }



    override fun setAlpha(alpha: Int) {
        TODO("Not yet implemented")
    }

    override fun getOpacity(): Int {
        TODO("Not yet implemented")
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("Not yet implemented")
    }

    companion object{
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private val symbolDrawables = mapOf<CardSymbol, Drawable>(
                CardSymbol.CLUBS to context.resources.getDrawable(R.drawable.im_cards_clubs_symbol, null),
                CardSymbol.SPADES to context.resources.getDrawable(R.drawable.im_cards_spades_symbol, null),
                CardSymbol.HEARTS to context.resources.getDrawable(R.drawable.im_cards_hearts_symbol, null),
                CardSymbol.DIAMONDS to context.resources.getDrawable(R.drawable.im_cards_diamonds_symbol, null)
        )

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private val symbolRotatedDrawables = mapOf<CardSymbol, Drawable>(
                CardSymbol.CLUBS to context.resources.getDrawable(R.drawable.im_cards_clubs_symbol_upside_down, null),
                CardSymbol.SPADES to context.resources.getDrawable(R.drawable.im_cards_spades_symbol_upside_down, null),
                CardSymbol.HEARTS to context.resources.getDrawable(R.drawable.im_cards_hearts_symbol_upside_down, null),
                CardSymbol.DIAMONDS to context.resources.getDrawable(R.drawable.im_cards_diamonds_symbol_upside_down, null)
        )


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private val cardDrawables = mapOf<CardSymbol, Drawable>(
                CardSymbol.CLUBS to context.resources.getDrawable(R.drawable.cards_black_card_front_blank, null),
                CardSymbol.SPADES to context.resources.getDrawable(R.drawable.cards_black_card_front_blank, null),
                CardSymbol.HEARTS to context.resources.getDrawable(R.drawable.cards_red_card_front_blank, null),
                CardSymbol.DIAMONDS to context.resources.getDrawable(R.drawable.cards_red_card_front_blank, null)
        )

        private fun figureToText(figure: CardFigure): String {
            return when(figure) {
                CardFigure.ACE -> "A"
                CardFigure.N2 -> "2"
                CardFigure.N3 -> "3"
                CardFigure.N4 -> "4"
                CardFigure.N5 -> "5"
                CardFigure.N6 -> "6"
                CardFigure.N7 -> "7"
                CardFigure.N8 -> "8"
                CardFigure.N9 -> "9"
                CardFigure.N10 -> "="
                CardFigure.JACK -> "J"
                CardFigure.QUEEN -> "Q"
                CardFigure.KING -> "K"
            }
        }

        private fun symbolToColor(symbol: CardSymbol): Int {
            return when(symbol) {
                CardSymbol.CLUBS -> ContextCompat.getColor(context,  R.color.black)
                CardSymbol.DIAMONDS -> ContextCompat.getColor(context,  R.color.black)
                CardSymbol.HEARTS -> ContextCompat.getColor(context,  R.color.red_card_color)
                CardSymbol.SPADES -> ContextCompat.getColor(context,  R.color.red_card_color)
            }
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private val cardFont = TextPaint().apply{
            typeface = ResourcesCompat.getFont(context, R.font.cards_font)
        }
    }
}