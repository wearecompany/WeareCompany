package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class ProgressHeaderFalseViewHodel (v : View): RecyclerView.ViewHolder(v) {

    private val header = itemView.findViewById<TextView>(R.id.progress_header)
    private val countview = itemView.findViewById<TextView>(R.id.progress_header_count)

    fun bindWithView(count: Int ) {
        header.text = "진행완료"
        countview.text = count.toString()
    }
}