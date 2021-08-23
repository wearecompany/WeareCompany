package com.weare.wearecompany.data.bottomnav.estimate.progress.data

import java.io.Serializable

data class ProgressMany (
    val request_idx: String,
    val request_log_idx: String,
    val expert_type: Int,
    val expert_idx: String,
    val sub_category: String,
    val user_nickname: String,
    val user_thumbnail: String,
    val price: Int,
    val datetime: String,
    val thumbnail : List<String>,
    val view_status : Boolean,
    val review_status : Boolean,
    val on_progress : Boolean,
    val refund_status : Boolean
): Serializable