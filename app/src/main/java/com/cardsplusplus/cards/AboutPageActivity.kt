package com.cardsplusplus.cards

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AboutPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_page)

        var listView = findViewById<ListView>(R.id.credits_list_view)

        var arrayList = java.util.ArrayList<CreditsData>()
        arrayList.add(CreditsData("Lead developer", "Hubert", "Kowalik", 12f, true))
        arrayList.add(CreditsData("Sound", "Hubert", "Kowalik", -9f, false))
        arrayList.add(CreditsData("Backend", "Hubert", "Kowalik", 21f, true))
        arrayList.add(CreditsData("UI design mastermind", "Hubert", "Kowalik", 126f, false))
        arrayList.add(CreditsData("Graphics stealing", "Hubert", "Kowalik", -1f, true))
        arrayList.add(CreditsData("Marketing", "Hubert", "Kowalik", 32f, true))


        var adapter = CreditsListAdapter(this, arrayList)
        listView.adapter = adapter
    }
}


class CreditsListAdapter(private val context: Context, private val dataArrayList: java.util.ArrayList<CreditsData>) : BaseAdapter() {
    private lateinit var label: TextView
    private lateinit var contentTop: TextView
    private lateinit var contentBottom: TextView
    private lateinit var contentFrame: FrameLayout
    private lateinit var contentImage: ImageView
    private lateinit var contentLayout: LinearLayout

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = LayoutInflater.from(context).inflate(R.layout.credits_list_item, parent, false)
        label = convertView.findViewById((R.id.credits_label))
        contentTop = convertView.findViewById((R.id.credits_content_top))
        contentBottom = convertView.findViewById((R.id.credits_content_bottom))
        contentFrame = convertView.findViewById(R.id.credits_content_frame_layout)
        contentImage = convertView.findViewById(R.id.credits_card_img)
        contentLayout = convertView.findViewById(R.id.credits_content_text_layout)

        when(dataArrayList[position].isFront){
            true -> {
                contentLayout.visibility = View.VISIBLE
                when(dataArrayList[position].isRed){
                    true -> contentImage.setImageResource(R.drawable.cards_red_card_front_blank)
                    false -> contentImage.setImageResource(R.drawable.cards_black_card_front_blank)
                }
            }
            false -> {
                contentLayout.visibility = View.INVISIBLE
                when(dataArrayList[position].isRed){
                    true -> contentImage.setImageResource(R.drawable.cards_red_card_back)
                    false -> contentImage.setImageResource(R.drawable.cards_black_card_back)
                }
            }
        }


        label.text = dataArrayList[position].label
        contentTop.text = dataArrayList[position].contentTop
        contentBottom.text = dataArrayList[position].contentBottom
        contentFrame.rotation = dataArrayList[position].rotation

        contentFrame.setOnClickListener(){
            flipCard(position)
            this.notifyDataSetChanged()
        }

        return convertView
    }

    override fun getItem(position: Int): Any {
        return dataArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataArrayList.count()
    }

    private fun flipCard(ind: Int) {
        dataArrayList[ind].isFront = !dataArrayList[ind].isFront
    }

}

class CreditsData(var label: String, var contentTop: String,
                  var contentBottom: String, var rotation: Float,
                  var isRed: Boolean, var isFront: Boolean = false)