package com.cardsplusplus.cards.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.cardsplusplus.cards.R

class MenuItemAdapter(private val menuItems: ArrayList<MenuCardData>) :
        RecyclerView.Adapter<MenuItemAdapter.MenuCardItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCardItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MenuCardItemHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: MenuCardItemHolder, position: Int) {
        val creditData: MenuCardData = menuItems[position]
        holder.bind(creditData)
    }

    inner class MenuCardItemHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_item_card, parent,false)) {

        private var btn: ImageButton = itemView.findViewById((R.id.main_menu_item_card_btn))

        fun bind(data: MenuCardData) {
            btn.setBackgroundResource(data.imgSrc)
            btn.setOnClickListener(data.listener)
        }

    }

}

class MenuCardData(var imgSrc: Int, var listener: View.OnClickListener)