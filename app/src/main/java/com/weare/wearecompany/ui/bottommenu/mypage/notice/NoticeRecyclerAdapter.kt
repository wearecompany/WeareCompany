package com.weare.wearecompany.ui.bottommenu.mypage.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.notice

class NoticeRecyclerAdapter(private val data: ArrayList<notice>): RecyclerView.Adapter<NoticeViewHodel>() {


    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: notice)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHodel {
        return NoticeViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_notice, parent, false))
    }

    override fun onBindViewHolder(holder: NoticeViewHodel, position: Int) {
        val item = data[position]

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}