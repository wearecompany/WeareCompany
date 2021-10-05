package com.weare.wearecompany.ui.listcontainer.studio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.ui.listcontainer.model.ModelClipViewHodel

class StudioMapClipRecyerViewAdapter(private val data: ArrayList<clip>) : RecyclerView.Adapter<StudioMapClipViewHodel>() {

    //ClickListener
    /*interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: clip)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }*/
    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioMapClipViewHodel {
        return StudioMapClipViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_map_clip, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StudioMapClipViewHodel, position: Int) {
        val item = data[position]

        /*holder.clip.setOnClickListener {
            itemClickListener.onClick(it,position,item)
        }*/
       /* holder.clipimge.setOnClickListener{
            itemClickListener.onClick(it,position,item)
        }
        holder.clip.setOnClickListener{
            itemClickListener.onClick(it,position,item)
        }*/

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}