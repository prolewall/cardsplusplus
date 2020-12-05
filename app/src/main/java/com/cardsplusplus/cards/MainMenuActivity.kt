package com.cardsplusplus.cards

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi

class MainMenuActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val quitBtn = findViewById<Button>(R.id.quit_btn)
        quitBtn.setOnClickListener{
            finish()
        }
        quitBtn.background = getDrawable(R.drawable.cards_red_card_front_blank)

        val aboutBtn = findViewById<Button>((R.id.about_btn))
        aboutBtn.setOnClickListener{
            val intent = Intent(this, AboutPageActivity::class.java)
            startActivity(intent)
        }

        val settingsBtn = findViewById<ImageButton>((R.id.settings_btn))
        settingsBtn.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }
}