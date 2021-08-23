package com.weare.wearecompany.ui.bottommenu.main.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class HomeListCaViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val day = itemView.findViewById<TextView>(R.id.category_text)
    fun bindWith(Item: String) {

        day.text = Item

    }
}