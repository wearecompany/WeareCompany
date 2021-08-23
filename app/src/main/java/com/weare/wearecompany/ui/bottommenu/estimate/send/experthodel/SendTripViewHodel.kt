package com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import java.text.DecimalFormat

class SendTripViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val send_time = itemView.findViewById<TextView>(R.id.send_trip_call_time)
    private val people = itemView.findViewById<TextView>(R.id.send_trip_people)
    private val time = itemView.findViewById<TextView>(R.id.send_trip_time)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.send_trip_recyclerview)

    private lateinit var dataAdapter: SendExpertRecyclerViewAdapter
    private lateinit var dtlist: ArrayList<String>


    fun bindWithView(item: SendAllDate, context: Context, onClickListener: View.OnClickListener) {
        dtlist = ArrayList<String>()

        send_time.text = item.send_time
        people.text = item.head_count
        time.text = item.time
        val dt = item.date.split(",")
        for (i in dt) {
            dtlist.add(i)

        }

        dataAdapter = item.date_status?.let { SendExpertRecyclerViewAdapter(dtlist, it) }!!
        if (dtlist.size > 1) {
            recyclerView.layoutManager = GridLayoutManager(context, 3)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
        }
        recyclerView.adapter = dataAdapter

    }
}