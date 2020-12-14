package com.cardsplusplus.cards.game

import android.graphics.Color

class Player(val name: String, val maxTime: Int) {
    val isPlaying = false
    val isFinished = false
    val remainingTime = maxTime
    val hand: PlayersHand = PlayersHand()

    fun update(){

    }
}