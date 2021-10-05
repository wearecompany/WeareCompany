package com.weare.wearecompany.data.bottomnav.estimate.receive.data

import java.io.Serializable

data class ReceiveStudioPage (
    val reserve_idx: String,
    val expert_idx: String,
    val expert_user_name: String,
    val expert_name: String,
    val expert_category: String,
    val room_image: String,
    val room_name: String,
    val reserve_dt: String,
    val reserve_time: Int,
    val reserve_time_term: String,
    val reserve_headcount: Int,
    val reserve_contents: String,
    val reserve_add_contents: String,
    val reserve_price: Int
) : Serializable