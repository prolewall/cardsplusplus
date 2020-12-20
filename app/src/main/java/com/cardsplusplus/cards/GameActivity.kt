package com.cardsplusplus.cards

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cardsplusplus.cards.game.GameBoard
import com.cardsplusplus.cards.game.GameMode
import com.cardsplusplus.cards.game.GameOptions

class GameActivity : AppCompatActivity(){
    private lateinit var sensorManager: SensorManager

    private var gyroscopeSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = GameOptions(3, 1, 0, 300, 5, GameMode.FREE_PLAY)

        setContentView(GameBoard(this, options))



    }



}