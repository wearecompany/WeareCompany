package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate

class RefundShopHodel (v: View) : RecyclerView.ViewHolder(v) {

    private val date_term = itemView.findViewById<TextView>(R.id.send_shop_date_term)
    private val start_time = itemView.findViewById<TextView>(R.id.send_shop_start_time)
    private val view_status = itemView.findViewById<ImageView>(R.id.shop_view_status)
    private val end_tim = itemView.findViewById<TextView>(R.id.send_shop_end_time)
    private val price = itemView.findViewById<TextView>(R.id.estimate_shop_price)
    private val pricetitle = itemView.findViewById<TextView>(R.id.estimate_shop_price_title)
    private val category = itemView.findViewById<TextView>(R.id.receive_shop_category)
    private val and = itemView.findViewById<View>(R.id.send_shop_time_and)
    private val type_text = itemView.findViewById<TextView>(R.id.receive_shop_type_text)
    private val type_background =
        itemView.findViewById<RelativeLayout>(R.id.receive_shop_type_background)

    fun bindWithView(item: ProgressAllDate, onClickListener: View.OnClickListener) {

        if (item.view_status) {
            view_status.setBackgroundResource(R.drawable.progress_status_no)
        } else {
            view_status.setBackgroundResource(R.drawable.refund_status)
        }

            category.visibility = View.GONE

        type_text.setTextColor(Color.parseColor("#000000"))
        type_text.setText("소품대여")
        type_text.setTextColor(Color.parseColor("#000000"))
        type_background.setBackgroundColor(Color.parseColor("#ffffff"))
        date_term.setText(item.date_term)
        start_time.setText(item.start_date)
        and.visibility = View.VISIBLE
        and.setBackgroundColor(Color.parseColor("#0f0f0f"))
        end_tim.visibility = View.VISIBLE
        end_tim.setText(item.end_date)
        price.setTextColor(Color.parseColor("#000000"))
        price.setText(item.price.toString())
        pricetitle.visibility = View.VISIBLE
        and.visibility = View.VISIBLE
    }

    fun viewStatus() {
        view_status.setBackgroundResource(R.drawable.progress_status_no)
    }
}