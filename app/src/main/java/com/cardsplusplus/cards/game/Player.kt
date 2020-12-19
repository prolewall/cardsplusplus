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

    fun draw(canvas: Canvas) {

    }
}