package com.weare.wearecompany.data.bottomnav.estimate.send.data

import java.io.Serializable

data class sendExpert (
    val reserve_idx: String,
    val send_time: String,
    val expert_type: Int,
    val head_count: String,
    val date_status: Boolean,
    val price: Int,
    val time: String,
    val date: String
): Serializable