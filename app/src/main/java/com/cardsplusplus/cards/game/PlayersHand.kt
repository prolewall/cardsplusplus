package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import kotlin.math.abs

class PlayersHand() {
    private val hand = mutableListOf<Card>()
    var selectedCardIndex: Int = 0
    var rect = Rect(0,0,0,0)
    var scale: Float = 1f

    fun addCard(card: Card){
        hand.add(card)
    }

    fun addMultipleCards(cards: List<Card>){
        hand.addAll(cards)
    }

    fun removeCardAt(index: Int): Card {
        return hand.removeAt(index)
    }

    fun removeSelectedCard(): Card {
        return this.removeCardAt(selectedCardIndex)
    }


    fun draw(canvas: Canvas, showCards: Boolean) {
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
        for(i: Int in 0 until (hand.count()-1)) {
            distFromMiddle = (middleIndex - i) * cardOffset
            if(i == selectedCardIndex) {
                topBound = rect.top
            }
            else{
                topBound = top
            }

            hand[i].draw(canvas, Point(rect.centerX() + distFromMiddle - hand[i].width/2, topBound), scale)
        }
    }
}