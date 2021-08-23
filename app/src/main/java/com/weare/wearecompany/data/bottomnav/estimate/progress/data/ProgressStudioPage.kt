package com.weare.wearecompany.data.bottomnav.estimate.progress.data

import java.io.Serializable

data class ProgressStudioPage (
    val reserve_idx: String,
    val reserve_tid: String,
    val expert_idx: String,
    val expert_user_name: String,
    val expert_name: String,
    val expert_category: String,
    val room_image: String,
    val complete: Int,
    val bill_method: String,
    val bill_date: String,
    val refund_money: Int,
    val refund_status: Int,
    val room_name: String,
    val reserve_dt: String,
    val reserve_time: Int,
    val reserve_time_term: String,
    val reserve_headcount: Int,
    val reserve_contents: String,
    val reserve_add_contents: String,
    val reserve_price: Int,
    val reserve_add_price: Int,
    val reserve_add_price_contents: String,
    val reserve_final_price: Int
): Serializable