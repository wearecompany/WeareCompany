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
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendExpertRecyclerViewAdapter
import java.text.DecimalFormat

class ReceiveTripViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val price = itemView.findViewById<TextView>(R.id.estimate_trip_price)
    private val pricetitle = itemView.findViewById<TextView>(R.id.estimate_trip_price_title)
    private val people = itemView.findViewById<TextView>(R.id.send_trip_people)
    private val time = itemView.findViewById<TextView>(R.id.send_trip_time)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.send_trip_recyclryvlew)
    private val type_text = itemView.findViewById<TextView>(R.id.receive_trip_type_text)
    private val category = itemView.findViewById<TextView>(R.id.receive_trip_category)

    private lateinit var dataAdapter: SendExpertRecyclerViewAdapter
    private lateinit var dtlist: ArrayList<String>

    private val dec = DecimalFormat("#,###")

    fun bindWithView(item: ReceiveAllDate, context: Context, onClickListener: View.OnClickListener) {
        dtlist = ArrayList<String>()

        category.setTextColor(Color.parseColor("#fec394"))
        time.text = item.time
        people.text = item.head_count
        price.setTextColor(Color.parseColor("#0f0f0f"))
        price.text = dec.format(item.price)
        pricetitle.visibility = View.VISIBLE
        dtlist.add(item.date)
        dataAdapter = SendExpertRecyclerViewAdapter(dtlist,true)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView.adapter = dataAdapter

    }
}