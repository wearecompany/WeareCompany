package com.weare.wearecompany.ui.bottommenu.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class HomeListCaRecyclerViewAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<HomeListCaViewHodel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListCaViewHodel {
        return HomeListCaViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_home_list_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeListCaViewHodel, position: Int) {
        val item = this.data[position]

        holder.apply {
            bindWith(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}