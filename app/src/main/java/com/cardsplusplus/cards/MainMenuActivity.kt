/*
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

        val playBtn = findViewById<Button>((R.id.play_btn))
        playBtn.setOnClickListener{
            val intent = Intent(this, PlayMenuActivity::class.java)
            startActivity(intent)
        }

    }
}*/

package com.cardsplusplus.cards

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cardsplusplus.cards.fragments.MainMenuCardFragment


class MainMenuActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_menu)

        var arrayList = java.util.ArrayList<MainMenuCardData>()
        arrayList.add(MainMenuCardData(R.drawable.im_play_button, View.OnClickListener {
            val intent = Intent(this, PlayMenuActivity::class.java)
            startActivity(intent)
        }))
        arrayList.add(MainMenuCardData(R.drawable.ic_setting_icon, View.OnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }))
        arrayList.add(MainMenuCardData(R.drawable.ic_about_icon, View.OnClickListener {
            val intent = Intent(this, AboutPageActivity::class.java)
            startActivity(intent)
        }))
        arrayList.add(MainMenuCardData(R.drawable.ic_exit_button, View.OnClickListener {
            finish()
        }))

        viewPager = findViewById(R.id.main_menu_viewpager)

        val pagerAdapter = MainMenuItemAdapter(arrayList)
        viewPager.adapter = pagerAdapter
    }

}

class MainMenuItemAdapter(private val menuItems: ArrayList<MainMenuCardData>) :
        RecyclerView.Adapter<MainMenuItemAdapter.MenuCardItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCardItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MenuCardItemHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: MenuCardItemHolder, position: Int) {
        val creditData: MainMenuCardData = menuItems[position]
        holder.bind(creditData)
    }

    inner class MenuCardItemHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_item_card, parent,false)) {

        private var btn: ImageButton = itemView.findViewById((R.id.main_menu_item_card_btn))

        fun bind(data: MainMenuCardData) {
            btn.setBackgroundResource(data.imgSrc)
            btn.setOnClickListener(data.listener)
        }

    }

}

class MainMenuCardData(var imgSrc: Int, var listener: View.OnClickListener)
/*class MainMenuActivity : AppCompatActivity() {
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

        val playBtn = findViewById<Button>((R.id.play_btn))
        playBtn.setOnClickListener{
            val intent = Intent(this, PlayMenuActivity::class.java)
            startActivity(intent)
        }


    }
}*/

