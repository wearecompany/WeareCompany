package com.weare.wearecompany.ui.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.chatting.data.send

class ChatUserMsgViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    val msg = itemView.findViewById<TextView>(R.id.send_user_text)
    private val day = itemView.findViewById<TextView>(R.id.send_user_day)
    private val time = itemView.findViewById<TextView>(R.id.send_user_time)
    fun bindWithView(Item: send, onClickListener: View.OnClickListener) {

        msg.text = Item.msg
        day.text = Item.send_day
        time.text = Item.send_time

    }
}