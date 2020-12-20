package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Color

class Player(val name: String, symbol: CardSymbol) {
    val isPlaying = false
    val isFinished = false
    val changeScreenCard = Card(symbol, CardFigure.ACE)
    val hand: PlayersHand = PlayersHand()

    init {

    }

    fun update(){

    }

    fun drawCardFrom(deck: DeckOfCards) {
        this.hand.addCard(deck.takeCard())
    }

    fun playSelectedCardTo(deck: DeckOfCards) {
        deck.putCardOnTop(this.hand.removeSelectedCard())
    }

}