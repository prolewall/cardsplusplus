package com.cardsplusplus.cards.game

import android.graphics.Canvas
import android.os.Build
import androidx.annotation.RequiresApi

class DeckOfCards(val cardsFront: Boolean) {
    val cards = mutableListOf<Card>()

    fun update(){

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun draw(canvas: Canvas) {
        if(cards.isEmpty()) {

        }
        else {
            cards[0].pos.x = 100
            cards[0].pos.y = 120
            cards[0].draw(canvas)
        }
    }

    fun putCardOnTop(card: Card){
        cards.add(0, card)
    }

    fun putCardOnBottom(card: Card){
        cards.add(card)
    }

    fun drawCard(): Card {
        return cards.removeAt(cards.count() - 1)
    }

}