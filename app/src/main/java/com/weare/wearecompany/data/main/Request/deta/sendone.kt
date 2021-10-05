package com.weare.wearecompany.data.main.Request.deta

import java.io.Serializable

data class sendone (
    var reserve_idx: String,
    var expert_name: String,
    var expert_type: Int,
    var send_time: String,
    var room_name: String,
    var head_count: String,
    var price: Int,
    var time: String,
    var date: String
) : Serializable