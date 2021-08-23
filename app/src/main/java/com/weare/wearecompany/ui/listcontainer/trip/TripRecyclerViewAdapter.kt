package com.weare.wearecompany.ui.listcontainer.trip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.trip.data.trip
import com.weare.wearecompany.data.model.list.data.Dmodel

class TripRecyclerViewAdapter(private val tripList: ArrayList<trip>): RecyclerView.Adapter<TripViewHodel>() {

    var mSize = 0
    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: trip)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    fun getSize(): Int {
        return mSize
    }

    fun setSize(postition: Int) {
        mSize = postition
    }
    fun addItem(data: trip) {
        tripList.add(data)
        setSize(tripList.size)
        //갱신처리 반드시 해야함
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHodel {
        return TripViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_trip_list, parent, false))
    }

    override fun onBindViewHolder(holder: TripViewHodel, position: Int) {
        val item = this.tripList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {
            })
        }
    }

    override fun getItemCount(): Int {
        return tripList.size
    }
}