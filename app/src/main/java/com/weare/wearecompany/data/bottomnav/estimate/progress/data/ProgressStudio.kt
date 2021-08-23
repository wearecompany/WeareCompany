package com.weare.wearecompany.data.bottomnav.estimate.progress.data

import java.io.Serializable

data class ProgressStudio (
    val reserve_idx: String,
    val expert_type: Int,
    val expert_idx: String,
    val head_count: String,
    val room_name:String,
    val price: Int,
    val time: String,
    val date: String,
    val view_status : Boolean,
    val review_status : Boolean,
    val on_progress : Boolean,
    val refund_status : Boolean,
): Serializable