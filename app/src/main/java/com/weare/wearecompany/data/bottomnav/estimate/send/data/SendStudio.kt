package com.weare.wearecompany.data.bottomnav.estimate.send.data

import java.io.Serializable

data class sendStudio (
    val reserve_idx: String,
    val send_time: String,
    val room_name: String,
    val head_count: String,
    val date_status: Boolean,
    val price: Int,
    val time: String,
    val date: String
): Serializable