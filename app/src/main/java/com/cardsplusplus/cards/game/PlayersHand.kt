package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Rect


class PlayersHand() {
    private val hand = mutableListOf<PlayingCard>()
    var selectedCardIndex: Int = 0

    private var innerRect: Rect = Rect(0,0,0,0)
    var rect: Rect
        set(newRect: Rect) {
            innerRect = newRect
            updateHand()
        }
        get() = innerRect

    val handTop: Int
        get() = (rect.top + rect.height()*0.2).toInt()

    fun count(): Int {
        return hand.count()
    }

    fun addCard(card: PlayingCard){
        hand.add(card)
        updateHand()
    }

    fun addMultipleCards(cards: List<PlayingCard>){
        hand.addAll(cards)
        updateHand()
    }

    fun removeCardAt(index: Int): PlayingCard {
        val card =  hand.removeAt(index)
        updateHand()
        return card
    }

    fun removeSelectedCard(): PlayingCard {
        val card = this.removeCardAt(selectedCardIndex)
        shiftSelectedRight()
        updateHand()
        return card
    }

    fun getSelectedCard() : PlayingCard {
        return this.hand[selectedCardIndex]
    }

    fun shiftSelectedLeft() {
        if(hand.isNotEmpty()) {
            selectedCardIndex = (selectedCardIndex + 1) % hand.count()
        }
    }

    fun getLeftCard(): PlayingCard {
        return hand[(selectedCardIndex + 1) % hand.count()]
    }

    fun shiftSelectedRight() {
        if(hand.isNotEmpty()) {
            selectedCardIndex = (hand.count() + selectedCardIndex - 1) % hand.count()
        }
    }

    fun getRightCard(): PlayingCard {
        return hand[(hand.count() + selectedCardIndex - 1) % hand.count()]
    }

    fun draw(canvas: Canvas) {
        if(count() != 0){
            for(i: Int in 0 until (hand.count())) {
                hand[i].draw(canvas)
            }
        }

    }

    operator fun get(index: Int): Card {
        return hand[index]
    }

    fun updateHand() {
        if(count() != 0){
            val width: Int = rect.width()
            var cardOffset: Int = width / hand.count()
            val maxCardOffset: Int = (0.1 * rect.width()).toInt()

            if(cardOffset > maxCardOffset){
                cardOffset = maxCardOffset
            }

            var distFromMiddle: Int
            val middleIndex: Int = hand.count() / 2
            var topBound = handTop
            for(i: Int in 0 until (hand.count())) {
                distFromMiddle = (middleIndex - i) * cardOffset

                topBound = if(i == selectedCardIndex) {
                    rect.top
                } else{
                    handTop
                }
                hand[i].bounds.offsetTo(rect.centerX() + distFromMiddle - hand[i].width/2, topBound)
            }
        }
    }
}