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

class DeckOfCards(val cardsFront: Boolean): TouchableSurface{
    private val deck = mutableListOf<Card>()
    var pos: Point = Point(0,0)
    var scale: Float = 1f
    override var rect = Rect(0,0,0,0)

    val emptyDeckDrawable: Drawable = context.resources.getDrawable(R.drawable.im_cards_empty_deck, null)

    fun update(){

    }

    fun createFullDeck(){
        for(symbol: CardSymbol in CardSymbol.values()){
            for(figure: CardFigure in CardFigure.values()) {
                deck.add(Card(symbol, figure))
            }
        }
    }

    fun shuffle() {
        deck.shuffle()
    }

    fun draw(canvas: Canvas) {
        if(deck.isEmpty()) {
            val width = (0.2 * context.resources.displayMetrics.widthPixels).toInt()
            val height = (width * (100f/72f)).toInt()
            drawDrawable(canvas, emptyDeckDrawable, pos, width, height)
        }
        else {
            deck[0].draw(canvas, pos, scale, cardsFront)

        }
    }

    fun putCardOnTop(card: Card){
        deck.add(0, card)
    }

    fun putMultipleCardsOnTop(cards: List<Card>) {
        deck.addAll(cards)
    }

    fun putCardOnBottom(card: Card){
        deck.add(deck.count() - 1, card)
    }

    fun putMultipleCardsOnBottom(cards: List<Card>) {
        deck.addAll(deck.count() - 1, cards)
    }

    fun takeCard(): Card {
        return deck.removeAt(0)
    }

    fun takeMultipleCards(quantity: Int): List<Card> {
        val cards = mutableListOf<Card>()

        var i = 0
        while(i < quantity){
            cards.add(this.takeCard())
            i++
        }

        return cards.toList()
    }

}