package com.weare.wearecompany.data.bottomnav.estimate.progress.data

import java.io.Serializable

data class ProgressExpertPage (
    val reserve_idx: String,
    val reserve_status: String,
    val reserve_tid: String,
    val expert_idx: String,
    val expert_name: String,
    val expert_category: String,
    val expert_image: String,
    val expert_price: String,
    val expert_place: String,
    val bill_method: String,
    val bill_date: String,
    val refund_money: Int,
    val refund_status: Int,
    val expert_type: Int,
    val reserve_dt: String,
    val reserve_time: Int,
    val reserve_time_term: String,
    val reserve_headcount: Int,
    val reserve_contents: String,
    val reserve_add_contents: String,
    val reserve_price: Int
): Serializable