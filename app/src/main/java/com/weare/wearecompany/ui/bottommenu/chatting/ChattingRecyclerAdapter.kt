package com.weare.wearecompany.ui.bottommenu.chatting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.manual
import com.weare.wearecompany.data.chatting.ChatList
import com.weare.wearecompany.data.chatting.data.chatlist

class ChattingRecyclerAdapter(private val data: ArrayList<ChatList>) : RecyclerView.Adapter<ChattingViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: chatlist)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingViewHodel {
        return ChattingViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_chat_list, parent, false))
    }

    override fun onBindViewHolder(holder: ChattingViewHodel, position: Int) {
        val item = data[0].chat[position]

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position, item)
        }
        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return data[0].chat.size
    }
}