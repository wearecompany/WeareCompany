package com.weare.wearecompany.data.main.Request.deta

import java.io.Serializable

data class sendoneclickpage (
    var reserve_idx: String,
    var send_time: String,
    var expert_type: ArrayList<Int>,
    var price : String,
    var contents : String,
    var thumbnail : ArrayList<String>,
) : Serializable