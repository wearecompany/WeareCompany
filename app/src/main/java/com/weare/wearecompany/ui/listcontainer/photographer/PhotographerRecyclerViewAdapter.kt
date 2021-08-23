package com.weare.wearecompany.ui.listcontainer.photographer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.model.photographer.data.photo
import com.weare.wearecompany.data.List.trip.data.trip

class PhotographerRecyclerViewAdapter(private val photoList: ArrayList<photo>) : RecyclerView.Adapter<PhotographerViewHodel>() {
    var mSize = 0
    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: photo)
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
    fun addItem(data: photo) {
        photoList.add(data)
        setSize(photoList.size)
        //갱신처리 반드시 해야함
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotographerViewHodel {
        return PhotographerViewHodel(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_photographer_list, parent, false))
    }

    override fun onBindViewHolder(holder: PhotographerViewHodel, position: Int) {
        val item = this.photoList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}