package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.review

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendExpertRecyclerViewAdapter

class reviewTripHodel (v: View) : RecyclerView.ViewHolder(v) {
    private val price = itemView.findViewById<TextView>(R.id.estimate_trip_price)
    private val pricetitle = itemView.findViewById<TextView>(R.id.estimate_trip_price_title)
    private val people = itemView.findViewById<TextView>(R.id.send_trip_people)
    private val view_status = itemView.findViewById<ImageView>(R.id.trip_view_status)
    private val time = itemView.findViewById<TextView>(R.id.send_trip_time)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.send_trip_recyclryvlew)
    private val type_text = itemView.findViewById<TextView>(R.id.receive_trip_type_text)
    val category = itemView.findViewById<TextView>(R.id.receive_trip_category)

    private lateinit var dataAdapter: SendExpertRecyclerViewAdapter
    private lateinit var dtlist: ArrayList<String>

    fun bindWithView(item: ProgressAllDate, context: Context, onClickListener: View.OnClickListener) {
        dtlist = ArrayList<String>()

        if (item.view_status) {
            view_status.setBackgroundResource(R.drawable.progress_status_no)
        } else {
            view_status.setBackgroundResource(R.drawable.review_status)
        }

        if (item.review_status) {
            category.visibility = View.GONE
        } else {
            category.setBackgroundResource(R.drawable.user_request_send)
            category.setTextColor(Color.parseColor("#ffffff"))
            category.setText("후기작성")
        }
        type_text.setBackgroundResource(R.drawable.progress_status_true)
        type_text.setText("뷰티전문가")

        category.setTextColor(Color.parseColor("#ffffff"))
        time.text = item.time
        people.text = item.head_count
        price.setTextColor(Color.parseColor("#000000"))
        price.setText(item.price.toString())
        pricetitle.visibility = View.VISIBLE
        dtlist.add(item.date)
        dataAdapter = SendExpertRecyclerViewAdapter(dtlist,true)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView.adapter = dataAdapter
    }

    fun viewStatus() {
        view_status.setBackgroundResource(R.drawable.progress_status_no)
    }
}