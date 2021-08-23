package com.weare.wearecompany.data.bottomnav.estimate.receive.data

import java.io.Serializable

data class ReceiveShopPage (
    val reserve_idx: String,
    val expert_idx: String,
    val expert_user_name: String,
    val expert_name: String,
    val expert_category: String,
    val expert_image: String,
    val start_dt: String,
    val end_dt: String,
    val reserve_time_term: Int,
    val reserve_contents: String,
    val reserve_add_contents: String,
    val reserve_final_price: Int,
) : Serializable