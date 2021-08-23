package com.weare.wearecompany.data.bottomnav.estimate.send.data

import java.io.Serializable

data class SendExpertPage (
    val reserve_idx: String,
    var expert_idx: String,
    val expert_name: String,
    val expert_category: String,
    val expert_image: String,
    val expert_type: Int,
    val expert_price: String,
    val expert_place: String,
    val dt_status: Int,
    val reserve_dt: String,
    val reserve_time: Int,
    val time_zone: Int,
    val reserve_headcount: Int,
    val reserve_contents: String,
    val reserve_price: Int
) : Serializable