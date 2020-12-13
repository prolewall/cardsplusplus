package com.cardsplusplus.cards

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cardsplusplus.cards.ui.MenuCardData
import com.cardsplusplus.cards.ui.MenuItemAdapter


class MainMenuActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        var menuItems = java.util.ArrayList<MenuCardData>()
        menuItems.add(MenuCardData(R.drawable.ic_play_button, View.OnClickListener {
            val intent = Intent(this, PlayMenuActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(R.drawable.ic_setting_icon, View.OnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(R.drawable.ic_about_icon, View.OnClickListener {
            val intent = Intent(this, AboutPageActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(R.drawable.ic_exit_button, View.OnClickListener {
            finish()
        }))

        viewPager = findViewById(R.id.main_menu_viewpager)

        val pagerAdapter = MenuItemAdapter(menuItems)
        viewPager.adapter = pagerAdapter
    }

}



