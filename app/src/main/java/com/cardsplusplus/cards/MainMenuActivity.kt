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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cardsplusplus.cards.game.Card
import com.cardsplusplus.cards.game.CardSymbol
import com.cardsplusplus.cards.ui.MenuCardData
import com.cardsplusplus.cards.ui.MenuItemAdapter
import com.cardsplusplus.cards.ui.MenuItemCard


class MainMenuActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        var menuItems = java.util.ArrayList<MenuCardData>()
        menuItems.add(MenuCardData(MenuItemCard(App.context.getDrawable(R.drawable.ic_play_button),
        "P", Card.cardDrawables[CardSymbol.CLUBS], Card.blackCardColor,
                "Play", Card.redCardColor),
                View.OnClickListener {
            val intent = Intent(this, PlayMenuActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(MenuItemCard(App.context.getDrawable(R.drawable.ic_setting_icon_red),
                "S", Card.cardDrawables[CardSymbol.HEARTS], Card.redCardColor,
                "Settings", Card.blackCardColor),
                View.OnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(MenuItemCard(App.context.getDrawable(R.drawable.ic_about_icon),
                "A", Card.cardDrawables[CardSymbol.CLUBS], Card.blackCardColor,
                "About", Card.redCardColor),
                View.OnClickListener {
            val intent = Intent(this, AboutPageActivity::class.java)
            startActivity(intent)
        }))
        menuItems.add(MenuCardData(MenuItemCard(App.context.getDrawable(R.drawable.ic_exit_button_red),
                "Q", Card.cardDrawables[CardSymbol.HEARTS], Card.redCardColor,
                "Quit", Card.blackCardColor),
                View.OnClickListener {
            finish()
        }))

        viewPager = findViewById(R.id.main_menu_viewpager)

        val pagerAdapter = MenuItemAdapter(menuItems)
        viewPager.adapter = pagerAdapter
    }

}



