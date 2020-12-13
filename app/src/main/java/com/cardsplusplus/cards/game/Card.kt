package com.cardsplusplus.cards.game


import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.cardsplusplus.cards.R

enum class CardSymbol {
    DIAMONDS, HEARTS, SPADES, CLUBS
}

enum class CardFigure {
    ACE, N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING
}

class Card(val symbol: CardSymbol, val figure: CardFigure, val context: Context) : Drawable() {
    private val cardDrawable: Int = getCardDrawableId(symbol)
    private val symbolDrawable: Int = getSymbolDrawableId(symbol)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas) {
        val card = context.resources.getDrawable(cardDrawable, null)
        val symbol = context.resources.getDrawable(symbolDrawable, null)

        card.bounds = this.bounds
        card.draw(canvas)


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
        private fun getSymbolDrawableId(symbol: CardSymbol): Int{
            return when(symbol){
                CardSymbol.CLUBS -> R.drawable.im_cards_clubs_symbol
                CardSymbol.SPADES -> R.drawable.im_cards_spades_symbol
                CardSymbol.HEARTS -> R.drawable.im_cards_hearts_symbol
                CardSymbol.DIAMONDS -> R.drawable.im_cards_diamonds_symbol
            }
        }

        private fun getCardDrawableId(symbol: CardSymbol): Int{
            return when(symbol){
                CardSymbol.CLUBS -> R.drawable.cards_black_card_front_blank
                CardSymbol.SPADES -> R.drawable.cards_black_card_front_blank
                CardSymbol.HEARTS -> R.drawable.cards_red_card_front_blank
                CardSymbol.DIAMONDS -> R.drawable.cards_red_card_front_blank
            }
        }

    }

}