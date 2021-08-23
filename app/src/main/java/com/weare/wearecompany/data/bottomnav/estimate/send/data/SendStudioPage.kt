package com.weare.wearecompany.data.bottomnav.estimate.send.data

import java.io.Serializable

data class SendStudioPage(
    val reserve_idx: String,
    val expert_idx: String,
    val expert_user_name: String,
    val expert_name: String,
    val expert_category: String,
    val room_image: String,
    val room_name: String,
    val room_maxcount: Int,
    val dt_status: Int,
    val reserve_dt: String,
    val reserve_time: Int,
    val time_zone: Int,
    val reserve_headcount: Int,
    val reserve_contents: String,
    val reserve_price: Int
) : Serializable