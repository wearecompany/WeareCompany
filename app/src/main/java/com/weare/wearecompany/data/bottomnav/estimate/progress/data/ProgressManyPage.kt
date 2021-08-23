package com.weare.wearecompany.data.bottomnav.estimate.progress.data

import java.io.Serializable

data class ProgressManyPage (
    val request_idx: String,
    val request_log_idx: String,
    val expert_idx: String,
    val expert_name: String,
    val expert_image: String,
    val expert_type: String,
    val expert_category: String,
    val expert_price: String,
    val expert_place: String,
    val complete: String,
    val reserve_tid: String,
    val bill_method: String,
    val bill_date: String,
    val refund_money: Int,
    val refund_status: Int,
    val category: String,
    val price: Int,
    val user_nickname: String,
    val user_thumbnail: String,
    val datetime: String,
    val request_contents: String,
    val response_contents: String,
    val thumbnail : List<String>,
): Serializable