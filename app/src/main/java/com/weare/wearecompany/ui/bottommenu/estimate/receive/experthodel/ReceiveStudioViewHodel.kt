package com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveAllDate
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendExpertRecyclerViewAdapter
import java.text.DecimalFormat

class ReceiveStudioViewHodel(v: View) : RecyclerView.ViewHolder(v) {
    private val room = itemView.findViewById<TextView>(R.id.send_studio_room_name)
    private val price = itemView.findViewById<TextView>(R.id.send_studio_money)
    private val pricetitle = itemView.findViewById<TextView>(R.id.send_studio_date_title)
    private val people = itemView.findViewById<TextView>(R.id.send_studio_people)
    private val time = itemView.findViewById<TextView>(R.id.send_studio_time)
    private val category = itemView.findViewById<TextView>(R.id.receive_studio_category)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.send_studio_recyclryvlew)
    private val type_text = itemView.findViewById<TextView>(R.id.receive_studio_type_text)

    private lateinit var dataAdapter: SendExpertRecyclerViewAdapter
    private lateinit var dtlist: ArrayList<String>

    private val dec = DecimalFormat("#,###")

    fun bindWithView(item: ReceiveAllDate, context: Context, onClickListener: View.OnClickListener) {
        dtlist = ArrayList<String>()

        category.setTextColor(Color.parseColor("#fec394"))
        room.text = item.room_name
        time.text = item.time
        people.text = item.head_count
        price.setTextColor(Color.parseColor("#0f0f0f"))
        price.text = dec.format(item.price)
        pricetitle.visibility = View.VISIBLE
        price.text = item.price.toString()

        dtlist.add(item.date)
        dataAdapter = SendExpertRecyclerViewAdapter(dtlist,true)
        recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )

        recyclerView.adapter = dataAdapter

    }
}