package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Color

class Player(val name: String, val maxTime: Int = -1) {
    val isPlaying = false
    val isFinished = false
    val remainingTime = maxTime
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

    fun draw(canvas: Canvas) {

    }
}