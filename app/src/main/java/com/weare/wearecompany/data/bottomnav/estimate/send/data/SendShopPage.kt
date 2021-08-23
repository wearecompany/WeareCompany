package com.weare.wearecompany.data.bottomnav.estimate.send.data

import java.io.Serializable

data class SendShopPage (
    val reserve_idx: String,
    val expert_idx: String,
    val expert_user_name: String,
    val expert_name: String,
    val expert_category: String,
    val expert_image: String,
    val dt_status: Int,
    val start_dt: String,
    val end_dt: String,
    val reserve_day: Int,
    val reserve_contents: String
) : Serializable