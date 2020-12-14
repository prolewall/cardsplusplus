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
import com.cardsplusplus.cards.utils.*

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
        height = (width * (100f/72f)).toInt()
        textSize = (width * 0.15).toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas) {

        val width = (scale * width).toInt()
        val height = (scale * height).toInt()
        val textSize = scale * textSize
        //draw blank card
        drawDrawable(canvas, cardDrawables[symbol], pos, width, height)

        val symbolSize = (width*0.1).toInt()
        val symbolDistanceFromSide = (width*0.05).toInt()
        val symbolDistanceFromTop = (height*0.15).toInt()
        // top left symbol
        drawDrawable(canvas, symbolDrawables[symbol],
                Point(pos.x + symbolDistanceFromSide,pos.y + symbolDistanceFromTop),
                symbolSize, symbolSize)
        // bottom right symbol
        drawDrawable(canvas, symbolRotatedDrawables[symbol],
                Point(pos.x + width - symbolSize - symbolDistanceFromSide,
                        pos.y + height - symbolSize - symbolDistanceFromTop),
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

        val figDistanceFromSide = (0.17*width).toInt()
        val figDistanceFromTop = (0.15*width).toInt()
        drawFigure(canvas, Point(pos.x + figDistanceFromSide, pos.y + figDistanceFromTop),
        width - 2 * figDistanceFromSide, height - 2 * figDistanceFromSide)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawFigure(canvas: Canvas, pos: Point, width: Int, height: Int) {
        val symbolSize: Int = (0.35 * width).toInt()
        when(figure) {
            CardFigure.ACE -> drawAce(canvas, pos, width, height)
            CardFigure.N2 -> draw2(canvas, pos, width, height, symbolSize)
            CardFigure.N3 -> draw3(canvas, pos, width, height, symbolSize)
            CardFigure.N4 -> draw4(canvas, pos, width, height, symbolSize)
            CardFigure.N5 -> draw5(canvas, pos, width, height, symbolSize)
            CardFigure.N6 -> draw6(canvas, pos, width, height, symbolSize)
            CardFigure.N7 -> draw7(canvas, pos, width, height, symbolSize)
            CardFigure.N8 -> draw8(canvas, pos, width, height, symbolSize)
            CardFigure.N9 -> draw9(canvas, pos, width, height, symbolSize)
            CardFigure.N10 -> draw10(canvas, pos, width, height, symbolSize)
            CardFigure.JACK -> TODO()
            CardFigure.QUEEN -> TODO()
            CardFigure.KING -> TODO()
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawAce(canvas: Canvas, pos: Point, width: Int, height: Int) {
        drawCenterSymbol(canvas, pos, width, height, (0.8*width).toInt())
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw2(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerX = getCenter(pos.x, size, width)

        drawSymbols(canvas, pos, width, height, size,
        listOf(Point(centerX, pos.y)),
        listOf(Point(centerX, getBorder(pos.y, size, height))))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw3(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        drawCenterSymbol(canvas, pos, width, height, size)
        draw2(canvas, pos, width, height, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw4(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val rightBorderX = getBorder(pos.x, size, width)
        val bottomBorderY = getBorder(pos.y, size, height)

        drawSymbols(canvas, pos, width, height, size,
                listOf(Point(pos.x, pos.y), Point(rightBorderX, pos.y)),
                listOf(Point(pos.x, bottomBorderY), Point(rightBorderX, bottomBorderY)))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw5(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        drawCenterSymbol(canvas, pos, width, height, size)
        draw4(canvas, pos, width, height, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw6(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerY = getCenter(pos.y, size, height)
        val borderX = getBorder(pos.x, size, width)

        drawSymbols(canvas, pos, width, height, size,
                listOf(Point(pos.x, centerY), Point(borderX, centerY)),
                listOf())

        draw4(canvas, pos, width, height, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw7(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerX = getCenter(pos.x, size, width)
        drawSymbols(canvas, pos, width, height, size,
                listOf(Point(centerX, (pos.y + 0.2*height).toInt())),
                listOf())

        draw6(canvas, pos, width, height, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw8(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerX = getCenter(pos.x, size, width)
        drawSymbols(canvas, pos, width, height, size,
                listOf(),
                listOf(Point(centerX, (pos.y + height - 0.2*height - size).toInt())))

        draw7(canvas, pos, width, height, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw9(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        draw4SymbolsPerSide(canvas, pos, width, height, size)
        drawCenterSymbol(canvas, pos, width, height, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw10(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerX = getCenter(pos.x, size, width)

        draw4SymbolsPerSide(canvas, pos, width, height, size)
        drawSymbols(canvas, pos, width, height, size,
                listOf(Point(centerX, (pos.y + 0.12*height).toInt())),
                listOf(Point(centerX, (pos.y + height - 0.12*height - size).toInt())))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawSymbols(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int,
                            symbolsPos: List<Point>, rotatedSymbolsPos: List<Point>) {
        drawMultipleDrawables(canvas, symbolDrawables[symbol], size, size, symbolsPos)
        drawMultipleDrawables(canvas, symbolRotatedDrawables[symbol], size, size, rotatedSymbolsPos)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawCenterSymbol(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val centerX = getCenter(pos.x, size, width)
        val centerY = getCenter(pos.y, size, height)

        drawDrawable(canvas, symbolDrawables[symbol], Point(centerX, centerY), size, size)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun draw4SymbolsPerSide(canvas: Canvas, pos: Point, width: Int, height: Int, size: Int) {
        val borderX = getBorder(pos.x, size, width)
        val distFromCenter = (0.02 * height).toInt()
        val row2Y = pos.y + height/2 - size - distFromCenter
        val row3Y = pos.y + height/2 + distFromCenter

        draw4(canvas, pos, width, height, size)

        drawSymbols(canvas, pos, width, height, size,
                listOf(Point(pos.x, row2Y), Point(borderX, row2Y)),
                listOf(Point(pos.x, row3Y), Point(borderX, row3Y)))

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
                CardSymbol.SPADES -> ContextCompat.getColor(context,  R.color.black)
                CardSymbol.HEARTS -> ContextCompat.getColor(context,  R.color.red_card_color)
                CardSymbol.DIAMONDS -> ContextCompat.getColor(context,  R.color.red_card_color)
            }
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private val cardFont = TextPaint().apply{
            typeface = ResourcesCompat.getFont(context, R.font.cards_font)
        }
    }
}
