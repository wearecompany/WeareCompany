package com.weare.wearecompany.data.bottomnav.estimate.receive

import java.io.Serializable

data class Receivepage (
    val request_idx: String,
    val request_log_idx: String,
    val expert_name: String,
    val expert_image: String,
    val expert_type: String,
    val expert_category: String,
    val expert_price: String,
    val expert_place: String,
    val category: String,
    val price: Int,
    val user_nickname: String,
    val user_thumbnail: String,
    val datetime: String,
    val request_contents: String,
    val response_contents: String,
    val thumbnail: List<String>,
) : Serializable