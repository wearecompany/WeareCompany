package com.weare.wearecompany.data.chatting

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.chatting.data.chat

data class ChatLog (
    @SerializedName("chat_log") val chat_log: ArrayList<chat>
    )

