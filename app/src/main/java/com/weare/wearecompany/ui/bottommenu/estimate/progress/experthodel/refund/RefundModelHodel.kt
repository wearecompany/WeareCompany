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

class RefundModelHodel (v: View) : RecyclerView.ViewHolder(v) {
    private val price = itemView.findViewById<TextView>(R.id.estimate_model_price)
    private val estimate_model_price_sub_title = itemView.findViewById<TextView>(R.id.estimate_model_price_sub_title)
    private val time = itemView.findViewById<TextView>(R.id.send_model_date_term)
    private val view_status = itemView.findViewById<ImageView>(R.id.model_view_status)
    private val recyclerView = itemView.findViewById<RecyclerView>(R.id.send_model_recyclryvlew)
    private val type_text = itemView.findViewById<TextView>(R.id.receive_model_type_text)
    private val category = itemView.findViewById<TextView>(R.id.receive_model_category)

    private lateinit var dataAdapter: SendExpertRecyclerViewAdapter
    private lateinit var dtlist: ArrayList<String>

    fun bindWithView(item: ProgressAllDate, context: Context, onClickListener: View.OnClickListener) {
        dtlist = ArrayList<String>()

        if (item.view_status) {
            view_status.setBackgroundResource(R.drawable.progress_status_no)
        } else {
            view_status.setBackgroundResource(R.drawable.refund_status)
        }
            category.visibility = View.GONE

        type_text.setBackgroundResource(R.drawable.refund_status_true)
        time.text = item.time
        price.setTextColor(Color.parseColor("#000000"))
        price.setText(item.price.toString())
        estimate_model_price_sub_title.visibility = View.VISIBLE
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