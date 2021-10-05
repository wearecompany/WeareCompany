package com.weare.wearecompany.ui.chat

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.chatting.data.send


class ChatRecyclerAdapterView(private val clip: ClipboardManager, val context: Context, private val dataList: ArrayList<send>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mSize = 0

    private val FORBIDDEN = -2
    private val NOTIFICATION = -1
    private val USER_TEXT = 0
    private val USER_IMAGE = 1
    private val EXPERT_TEXT = 2

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: send)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: ChatRecyclerAdapterView.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            USER_TEXT -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_user_msg, parent, false)
                return ChatUserMsgViewHodel(headerView)
            }
            USER_IMAGE -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_user_image, parent, false)
                return ChatUserImageViewHodel(headerView)
            }
            EXPERT_TEXT -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_expert_msg, parent, false)
                return ChatExpertMsgViewHodel(headerView)
            }
            NOTIFICATION -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_notification, parent, false)
                return ChatNotificationViewHodel(headerView)
            }
            FORBIDDEN -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_forbidden, parent, false)
                return ChatForbiddenViewHodel(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_expert_image, parent, false)
                return ChatExpertImageViewHodel(headerView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        when (holder) {
            is ChatNotificationViewHodel -> {
                holder.apply {
                    bindWithView()
                }
            }
            is ChatForbiddenViewHodel -> {
                holder.apply {
                    bindWithView()
                }
            }
            is ChatUserMsgViewHodel -> {
                holder.msg.setOnLongClickListener(object : View.OnLongClickListener{
                    override fun onLongClick(v: View?): Boolean {
                        val clipdata : ClipData = ClipData.newPlainText("label",(v as TextView).text)
                        clip.setPrimaryClip(clipdata)
                        Toast.makeText(context,"복사되었습니다.",Toast.LENGTH_SHORT).show()
                        return true
                    }

                })
                holder.itemView.setOnClickListener {
                    if (data != null) {
                        itemClickListener.onClick(it, position, data)
                    }
                }
                holder.apply {
                    bindWithView(data, View.OnClickListener {

                    })
                }
            }
            is ChatUserImageViewHodel -> {
                holder.itemView.setOnClickListener {
                    if (data != null) {
                        itemClickListener.onClick(it, position, data)
                    }
                }
                holder.apply {
                    bindWithView(data, View.OnClickListener {

                    })
                }
            }
            is ChatExpertMsgViewHodel -> {
                holder.itemView.setOnClickListener {
                    if (data != null) {
                        itemClickListener.onClick(it, position, data)
                    }
                }
                holder.apply {
                    bindWithView(data, View.OnClickListener {

                    })
                }
            }
            is ChatExpertImageViewHodel -> {
                holder.itemView.setOnClickListener {
                    if (data != null) {
                        itemClickListener.onClick(it, position, data)
                    }
                }
                holder.apply {
                    bindWithView(data, View.OnClickListener {

                    })
                }
            }
        }
    }

    fun getsize(): Int {
        return mSize
    }

    fun setSize(postition: Int) {
        mSize = postition
    }

    fun addItem(data: send) {
        dataList.add(data)
        setSize(dataList.size)
        //갱신처리 반드시 해야함
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return dataList[position].msg_type
    }
}