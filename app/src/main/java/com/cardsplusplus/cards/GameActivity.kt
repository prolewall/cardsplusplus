package com.cardsplusplus.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cardsplusplus.cards.game.GameBoard
import com.cardsplusplus.cards.game.GameMode
import com.cardsplusplus.cards.game.GameOptions

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = GameOptions(3, 1, 0, 300, 5, GameMode.FREE_PLAY)

        setContentView(GameBoard(this, options))
    }
}