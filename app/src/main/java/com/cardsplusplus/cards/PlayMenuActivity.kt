package com.cardsplusplus.cards

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.cardsplusplus.cards.game.Card
import com.cardsplusplus.cards.game.CardFigure
import com.cardsplusplus.cards.game.CardSymbol
import com.cardsplusplus.cards.ui.MenuCardData
import com.cardsplusplus.cards.ui.MenuItemAdapter


class PlayMenuActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_menu)

        var menuItems = java.util.ArrayList<MenuCardData>()
        menuItems.add(MenuCardData(R.drawable.ic_play_button, View.OnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(R.drawable.ic_internet, View.OnClickListener {
            val intent = Intent(this, OnlinePlayMenuActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(R.drawable.ic_back, View.OnClickListener {
            finish()
        }))

        viewPager = findViewById(R.id.play_menu_viewpager)

        val pagerAdapter = MenuItemAdapter(menuItems)
        viewPager.adapter = pagerAdapter
    }
}