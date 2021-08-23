package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class RefundHeaderFalseViewHodel (v : View): RecyclerView.ViewHolder(v) {

    private val header = itemView.findViewById<TextView>(R.id.progress_header)
    private val countview = itemView.findViewById<TextView>(R.id.progress_header_count)

    fun bindWithView(count: Int ) {
        header.text = "환불요청"
        countview.text = count.toString()
    }
}