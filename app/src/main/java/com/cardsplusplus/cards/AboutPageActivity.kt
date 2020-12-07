package com.cardsplusplus.cards

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AboutPageActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var creditsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)

        var arrayList = java.util.ArrayList<CreditsData>()
        arrayList.add(CreditsData("Lead developer", "Hubert", "Kowalik", 12f, true))
        arrayList.add(CreditsData("Sound", "Hubert", "Kowalik", -9f, false))
        arrayList.add(CreditsData("Backend", "Hubert", "Kowalik", 21f, true))
        arrayList.add(CreditsData("UI design mastermind", "Hubert", "Kowalik", 126f, false))
        arrayList.add(CreditsData("Graphics stealing", "Hubert", "Kowalik", -1f, true))
        arrayList.add(CreditsData("Marketing", "Hubert", "Kowalik", 32f, true))

        creditsList = findViewById<RecyclerView>(R.id.credits_list_view)
        linearLayoutManager = LinearLayoutManager(this)

        var adapter = CreditsListAdapter(arrayList)
        creditsList.layoutManager = linearLayoutManager
        creditsList.adapter = adapter
    }
}


class CreditsListAdapter(private val credits: ArrayList<CreditsData>) : RecyclerView.Adapter<CreditsListAdapter.CreditsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CreditsHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return credits.size
    }

    override fun onBindViewHolder(holder: CreditsHolder, position: Int) {
        val creditData: CreditsData = credits[position]
        holder.bind(creditData, position)
    }

    inner class CreditsHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.credits_list_item, parent,false)) {

        private var label: TextView = itemView.findViewById((R.id.credits_label))
        private var contentTop: TextView = itemView.findViewById((R.id.credits_content_top))
        private var contentBottom: TextView = itemView.findViewById((R.id.credits_content_bottom))
        private var contentFrame: FrameLayout = itemView.findViewById(R.id.credits_content_frame_layout)
        private var contentImage: ImageView = itemView.findViewById(R.id.credits_card_img)
        private var contentLayout: LinearLayout = itemView.findViewById(R.id.credits_content_text_layout)

        fun bind(data: CreditsData, pos: Int) {
            when(data.isFront){
                true -> {
                    contentLayout.visibility = View.VISIBLE
                    when(data.isRed){
                        true -> contentImage.setImageResource(R.drawable.cards_red_card_front_blank)
                        false -> contentImage.setImageResource(R.drawable.cards_black_card_front_blank)
                    }
                }
                false -> {
                    contentLayout.visibility = View.INVISIBLE
                    when(data.isRed){
                        true -> contentImage.setImageResource(R.drawable.cards_red_card_back)
                        false -> contentImage.setImageResource(R.drawable.cards_black_card_back)
                    }
                }
            }


            label.text = data.label
            contentTop.text = data.contentTop
            contentBottom.text = data.contentBottom
            contentFrame.rotation = data.rotation

            contentFrame.setOnClickListener{
                flipCard(data)
                this@CreditsListAdapter.notifyItemChanged(pos)
            }
        }

        private fun flipCard(data: CreditsData) {
            data.isFront = !data.isFront
        }
    }

}

class CreditsData(var label: String, var contentTop: String,
                  var contentBottom: String, var rotation: Float,
                  var isRed: Boolean, var isFront: Boolean = false)