package com.cardsplusplus.cards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val quitBtn = findViewById<Button>(R.id.quit_btn)
        quitBtn.setOnClickListener{
            finish()
        }

        val aboutBtn = findViewById<Button>((R.id.about_btn))
        aboutBtn.setOnClickListener{
            val intent = Intent(this, AboutPage::class.java)
            startActivity(intent)
        }

        val settingsBtn = findViewById<ImageButton>((R.id.settings_btn))
        settingsBtn.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }
}