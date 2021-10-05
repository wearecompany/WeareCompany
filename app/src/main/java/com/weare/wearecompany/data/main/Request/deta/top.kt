package com.weare.wearecompany.data.main.Request.deta

import java.io.Serializable

data class top (
    var reserve_idx: String,
    var reserve_type: Int,
    var reserve_status: Int,
    var expert_name: String,
    var expert_type: ArrayList<Int>,
    var send_time: String,
    var room_name: String,
    var head_count : String,
    var price: Int,
    var time : String,
    var date: String,
) : Serializable