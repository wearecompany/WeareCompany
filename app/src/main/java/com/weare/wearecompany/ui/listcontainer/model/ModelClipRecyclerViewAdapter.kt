package com.weare.wearecompany.ui.listcontainer.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip

class ModelClipRecyclerViewAdapter(private val data: ArrayList<clip>) : RecyclerView.Adapter<ModelClipViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: clip)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelClipViewHodel {
        return ModelClipViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_clip, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ModelClipViewHodel, position: Int) {
        val item = data[position]

        holder.cliplayout.setOnClickListener {
            itemClickListener.onClick(it,position,item)
        }
        holder.clipimge.setOnClickListener{
            itemClickListener.onClick(it,position,item)
        }
        holder.clip.setOnClickListener{
            itemClickListener.onClick(it,position,item)
        }

        holder.apply {
            bindWithView(item,View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}