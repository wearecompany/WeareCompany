package com.weare.wearecompany.ui.bottommenu.main.weekly

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class WeeklyCaViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val day = itemView.findViewById<TextView>(R.id.category_text)
    fun bindWith(Item: String) {

        day.text = Item

    }
}