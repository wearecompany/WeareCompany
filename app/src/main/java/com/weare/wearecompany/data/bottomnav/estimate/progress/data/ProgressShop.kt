package com.weare.wearecompany.data.bottomnav.estimate.progress.data

import java.io.Serializable

data class ProgressShop (
    val reserve_idx: String,
    val expert_type: Int,
    val expert_idx: String,
    val price: Int,
    val date_term: String,
    val start_date: String,
    val end_date: String,
    val view_status : Boolean,
    val review_status : Boolean,
    val on_progress : Boolean,
    val refund_status : Boolean,
): Serializable