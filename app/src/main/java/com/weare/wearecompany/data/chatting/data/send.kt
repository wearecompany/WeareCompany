package com.weare.wearecompany.data.chatting.data

import java.io.Serializable

data class send (
    var msg_idx : String,
    var msg_type: Int,
    var send_type: Int,
    var send_day: String,
    var send_time: String,
    var msg: String,
    var image_origin_url: String,
    var image_resize_url: String,
    var origin_width: Int,
    var origin_height: Int
): Serializable