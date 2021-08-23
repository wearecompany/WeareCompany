package com.weare.wearecompany.ui.hotpick.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.hotpick
import androidx.recyclerview.widget.RecyclerView

class HotpickListRecyeclerViewAdapter(private val hotpickList: ArrayList<hotpick>) : RecyclerView.Adapter<HotpickListViewHoder>(){


    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: hotpick)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotpickListViewHoder {
        return HotpickListViewHoder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_hotpick, parent, false))
    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return this.hotpickList.size
    }

    override fun onBindViewHolder(holder: HotpickListViewHoder, position: Int) {
        val item = this.hotpickList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {
            })
        }

        //holder.bindWithView(this.hotpickList[position])
    }

}