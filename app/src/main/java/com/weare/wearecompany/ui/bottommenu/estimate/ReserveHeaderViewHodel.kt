package com.weare.wearecompany.ui.bottommenu.estimate

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class ReserveHeaderViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val countview = itemView.findViewById<TextView>(R.id.individual_header_count)

    fun bindWithView(count: Int ) {
        countview.text = count.toString()

    }
}