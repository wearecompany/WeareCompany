package com.weare.wearecompany.data.main.Request.deta

import java.io.Serializable

data class receiveoneclick (
    var reserve_idx: String,
    var send_time: String,
    var expert_type: List<Int>,
    var price: Int,
    var time: String,
    var contents: String,
    var date: String,
    var thumbnail : ArrayList<String>,
) : Serializable