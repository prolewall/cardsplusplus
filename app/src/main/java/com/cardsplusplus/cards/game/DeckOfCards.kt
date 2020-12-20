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

    override var touchEvent: GameEvent = GameEvent.NONE

    var scale: Float = 1f
    override lateinit var rect: Rect

    val emptyDeckDrawable: Drawable = context.resources.getDrawable(R.drawable.im_cards_empty_deck, null)

    init {
        val width = (Card.WIDTH_PERCENTAGE*context.resources.displayMetrics.widthPixels).toInt()
        rect = Rect(0, 0, width, (Card.HEIGHT_RATIO * width).toInt())
    }

    fun update(){

    }

    override fun getEvent(pos: Point): GameEvent {
        return touchEvent
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
            drawDrawable(canvas, emptyDeckDrawable, rect)
        }
        else {
            deck[0].draw(canvas, Point(rect.left, rect.top), scale, cardsFront)

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

    fun takeAllCards(): List<Card> {
        return this.takeMultipleCards(this.deck.count())
    }

}