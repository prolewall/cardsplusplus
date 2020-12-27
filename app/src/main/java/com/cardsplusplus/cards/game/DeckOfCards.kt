package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R

class DeckOfCards(val cardsFront: Boolean){
    private val deck = mutableListOf<PlayingCard>()

    private var innerRect: Rect = Rect(0,0,0,0)
    var rect: Rect
        set(newRect) {
            innerRect = newRect
            update()
        }
        get() = innerRect

    val emptyDeckDrawable: Drawable = context.resources.getDrawable(R.drawable.im_cards_empty_deck, null)

    val pos: Point
        get() = Point(this.rect.left, this.rect.top)

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
        update()
    }

    fun draw(canvas: Canvas) {
        if(deck.isEmpty()) {
            emptyDeckDrawable.bounds = rect
            emptyDeckDrawable.draw(canvas)
        }
        else {
            deck[0].isFront = cardsFront

            deck[0].draw(canvas)
            deck[0].isFront = true
        }
    }

    fun putCardOnTop(card: PlayingCard){
        deck.add(0, card)
        update()
    }

    fun putMultipleCardsOnTop(cards: List<PlayingCard>) {
        deck.addAll(cards)
        update()
    }

    fun putCardOnBottom(card: PlayingCard){
        deck.add(deck.count() - 1, card)
    }

    fun putMultipleCardsOnBottom(cards: List<PlayingCard>) {
        deck.addAll(deck.count() - 1, cards)
    }

    fun takeCard(): PlayingCard {
        val card =  deck.removeAt(0)
        update()
        return card
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

    fun update() {
        if(deck.isNotEmpty()) {
            deck[0].bounds = rect
        }
        emptyDeckDrawable.bounds = rect
    }

}