package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.utils.drawDrawable

class DeckOfCards(val cardsFront: Boolean){
    private val deck = mutableListOf<PlayingCard>()



    var scale: Float = 1f
    lateinit var rect: Rect

    val emptyDeckDrawable: Drawable = context.resources.getDrawable(R.drawable.im_cards_empty_deck, null)

    init {
        val width = (PlayingCard.WIDTH_PERCENTAGE*context.resources.displayMetrics.widthPixels).toInt()
        rect = Rect(0, 0, width, (PlayingCard.HEIGHT_RATIO * width).toInt())
    }

    fun createFullDeck(){
        for(symbol: CardSymbol in CardSymbol.values()){
            for(figure: CardFigure in CardFigure.values()) {
                deck.add(PlayingCard(symbol, figure))
            }
        }
    }

    fun shuffle() {
        deck.shuffle()
    }

    fun draw(canvas: Canvas) {
        if(deck.isEmpty()) {
            drawDrawable(canvas, emptyDeckDrawable, rect)
        }
        else {
            deck[0].draw(canvas, Point(rect.left, rect.top), cardsFront)

        }
    }

    fun putCardOnTop(card: PlayingCard){
        deck.add(0, card)
    }

    fun putMultipleCardsOnTop(cards: List<PlayingCard>) {
        deck.addAll(cards)
    }

    fun putCardOnBottom(card: PlayingCard){
        deck.add(deck.count() - 1, card)
    }

    fun putMultipleCardsOnBottom(cards: List<PlayingCard>) {
        deck.addAll(deck.count() - 1, cards)
    }

    fun takeCard(): PlayingCard {
        return deck.removeAt(0)
    }

    fun takeMultipleCards(quantity: Int): List<PlayingCard> {
        val cards = mutableListOf<PlayingCard>()

        var i = 0
        while(i < quantity){
            cards.add(this.takeCard())
            i++
        }

        return cards.toList()
    }

    fun takeAllCards(): List<PlayingCard> {
        return this.takeMultipleCards(this.deck.count())
    }

}