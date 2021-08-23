package com.weare.wearecompany.data.chatting

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.chatting.data.chat
import com.weare.wearecompany.data.chatting.data.chatlist

data class ChatList (
    @SerializedName("chat") val chat: ArrayList<chatlist>
)