package com.weare.wearecompany.data.bottomnav.estimate.receive.data

import java.io.Serializable

data class receiveExpert (
    val reserve_idx: String,
    val expert_type: Int,
    val head_count: String,
    val price: Int,
    val time: String,
    val date: String
): Serializable