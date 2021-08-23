package com.weare.wearecompany.ui.bottommenu.main.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.event
import com.weare.wearecompany.data.main.data.guidelist
import com.weare.wearecompany.ui.bottommenu.main.home.HomeListCaViewHodel

class EventRecyclerViewAdapter(private val data: ArrayList<event>) : RecyclerView.Adapter<EventViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: event)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHodel {
        return EventViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_home_event, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventViewHodel, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }
        holder.apply {
            bindWith(item,View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}