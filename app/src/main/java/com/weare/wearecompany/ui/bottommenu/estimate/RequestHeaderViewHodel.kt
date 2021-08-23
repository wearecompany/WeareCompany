package com.weare.wearecompany.ui.bottommenu.estimate

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import java.util.concurrent.CountDownLatch

class RequestHeaderViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val countview = itemView.findViewById<TextView>(R.id.many_header_count)

    fun bindWithView(count: Int ) {
        countview.text = count.toString()
    }
}