package com.cardsplusplus.cards.game

class PlayersHand {
    private val hand = mutableListOf<Card>()
    var selectedCardIndex: Int = 0

    fun addCard(card: Card){
        hand.add(card)
    }

    fun addMultipleCards(cards: MutableList<Card>){
        hand.addAll(cards)
    }

    fun removeCardAt(index: Int): Card {
        return hand.removeAt(index)
    }
}