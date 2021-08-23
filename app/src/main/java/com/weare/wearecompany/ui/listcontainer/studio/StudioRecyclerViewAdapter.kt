package com.weare.wearecompany.ui.listcontainer.studio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.model.photographer.data.photo
import com.weare.wearecompany.data.main.data.studio

class StudioRecyclerViewAdapter(private val studioList: ArrayList<studio>) : RecyclerView.Adapter<StudioViewHodel>() {

    var mSize = 0
    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: studio)
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
    fun addItem(data: studio) {
        studioList.add(data)
        setSize(studioList.size)
        //갱신처리 반드시 해야함
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioViewHodel {
        return StudioViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_list, parent, false))
    }

    override fun onBindViewHolder(holder: StudioViewHodel, position: Int) {
        val item = this.studioList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return studioList.size
    }
}