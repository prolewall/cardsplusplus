package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import kotlin.math.abs

class PlayersHand() {
    private val hand = mutableListOf<PlayingCard>()
    var selectedCardIndex: Int = 0
    var rect = Rect(0,0,0,0)
    var scale: Float = 1f

    fun count(): Int {
        return hand.count()
    }

    fun addCard(card: PlayingCard){
        hand.add(card)
    }

    fun addMultipleCards(cards: List<PlayingCard>){
        hand.addAll(cards)
    }

    fun removeCardAt(index: Int): PlayingCard {
        return hand.removeAt(index)
    }

    fun removeSelectedCard(): PlayingCard {
        val card = this.removeCardAt(selectedCardIndex)
        shiftSelectedRight()
        return card
    }

    fun shiftSelectedLeft() {
        if(hand.isNotEmpty()) {
            selectedCardIndex = (selectedCardIndex + 1) % hand.count()
        }
    }

    fun shiftSelectedRight() {
        if(hand.isNotEmpty()) {
            selectedCardIndex = (hand.count() + selectedCardIndex - 1) % hand.count()
        }
    }

    fun draw(canvas: Canvas, showCards: Boolean) {
        if(count() != 0){
            val width: Int = rect.width()
            val top: Int = (rect.top + rect.height()*0.2).toInt()
            var cardOffset: Int = width / hand.count()
            val maxCardOffset: Int = (0.1 * rect.width()).toInt()

            if(cardOffset > maxCardOffset){
                cardOffset = maxCardOffset
            }

            var distFromMiddle = 0
            val middleIndex = hand.count() / 2
            var topBound = top
            for(i: Int in 0 until (hand.count())) {
                distFromMiddle = (middleIndex - i) * cardOffset

                topBound = if(i == selectedCardIndex) {
                    rect.top
                } else{
                    top
                }

                hand[i].draw(canvas, Point(rect.centerX() + distFromMiddle - hand[i].width/2, topBound))
            }
        }

    }
}