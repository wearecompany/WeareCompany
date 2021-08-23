package com.weare.wearecompany.data.bottomnav.estimate.receive.data

import java.io.Serializable

data class receiveStudio (
    val reserve_idx: String,
    val room_name: String,
    val head_count: String,
    val price: Int,
    val time: String,
    val date: String
): Serializable