package com.cardsplusplus.cards.game

import android.graphics.Color
import com.cardsplusplus.cards.App.Companion.context
import com.cardsplusplus.cards.R
import com.cardsplusplus.cards.ui.CustomCard

class Player(val name: String, symbol: CardSymbol) {
    val isPlaying = false
    val isFinished = false
    val changeScreenCard: CustomCard
    val hand: PlayersHand = PlayersHand()

    init {
        val color = if(symbol == CardSymbol.DIAMONDS || symbol == CardSymbol.HEARTS) {
            Color.BLACK
        } else {
            context.resources.getColor(R.color.red_card_color)
        }

        changeScreenCard = CustomCard(PlayingCard.symbolDrawables[symbol], "A",
        Card.cardDrawables[symbol], PlayingCard.symbolToColor(symbol), name, color)
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