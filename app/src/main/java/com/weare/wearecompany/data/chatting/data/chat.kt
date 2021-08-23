package com.weare.wearecompany.data.chatting.data

import java.io.Serializable

data class chat (
    var msg_idx : String,
    var send_type: Int,
    var msg_type: String,
    var send_day: String,
    var send_time: String,
    var msg: String,
): Serializable