package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

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

class RefundStudioHodel (v: View) : RecyclerView.ViewHolder(v) {
    private val room = itemView.findViewById<TextView>(R.id.send_studio_room_name)
    private val view_status = itemView.findViewById<ImageView>(R.id.studio_view_status)
    private val price = itemView.findViewById<TextView>(R.id.send_studio_money)
    private val pricetitle = itemView.findViewById<TextView>(R.id.send_studio_date_title)
    private val people = itemView.findViewById<TextView>(R.id.send_studio_people)
    private val time = itemView.findViewById<TextView>(R.id.send_studio_time)
    val category = itemView.findViewById<TextView>(R.id.receive_studio_category)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.send_studio_recyclryvlew)
    private val type_text = itemView.findViewById<TextView>(R.id.receive_studio_type_text)

    private lateinit var dataAdapter: SendExpertRecyclerViewAdapter
    private lateinit var dtlist: ArrayList<String>
    private var view_status_type:Boolean = false

    fun bindWithView(item: ProgressAllDate, context: Context, onClickListener: View.OnClickListener) {
        dtlist = ArrayList<String>()

        if (item.view_status) {
            view_status.setBackgroundResource(R.drawable.progress_status_no)
        } else {
            view_status.setBackgroundResource(R.drawable.refund_status)
        }
        type_text.setBackgroundResource(R.drawable.refund_status_true)
        category.visibility = View.GONE

        room.text = item.room_name
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