package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate

class SendShopViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val date_term = itemView.findViewById<TextView>(R.id.send_shop_date_term)
    private val time_and = itemView.findViewById<View>(R.id.send_shop_time_and)
    private val start_time = itemView.findViewById<TextView>(R.id.send_shop_start_time)
    private val end_tim = itemView.findViewById<TextView>(R.id.send_shop_end_time)

    fun bindWithView(item: SendAllDate, onClickListener: View.OnClickListener) {

        if (item.date_status == true) {
            date_term.setText("협의 가능")
            start_time.visibility = View.VISIBLE
            start_time.setText("협의 가능")
        } else {
            date_term.setText(item.date_term)
            start_time.setText(item.start_date)
            time_and.visibility = View.VISIBLE
            end_tim.visibility = View.VISIBLE
            end_tim.setText(item.end_date)
        }
    }
}