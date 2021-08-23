package com.weare.wearecompany.ui.bottommenu.main.weekly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.bottommenu.main.home.HomeListCaViewHodel

class WeeklyCaRecyclerViewAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<WeeklyCaViewHodel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyCaViewHodel {
        return WeeklyCaViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_weekly_list_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeeklyCaViewHodel, position: Int) {
        val item = this.data[position]

        holder.apply {
            bindWith(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}