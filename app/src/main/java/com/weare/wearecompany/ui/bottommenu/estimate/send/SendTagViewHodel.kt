package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class SendTagViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val dttext = itemView.findViewById<TextView>(R.id.tag_text)
    fun bindWithView(Item: String) {

        dttext.text = Item

    }
}