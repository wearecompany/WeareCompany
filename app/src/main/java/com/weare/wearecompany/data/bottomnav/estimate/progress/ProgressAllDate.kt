package com.weare.wearecompany.data.bottomnav.estimate.progress

import java.io.Serializable

data class ProgressAllDate (
    var type : Int,
    var reserve_idx: String,
    var expert_idx: String,
    var request_idx: String,
    var request_log_idx: String,
    var room_name: String,
    var head_count: String,
    var expert_type: Int?,
    var price: Int?,
    var time: String,
    var date: String,
    var date_term: String,
    var start_date: String,
    var end_date: String,
    var sub_category: String,
    var user_nickname: String,
    var user_thumbnail: String,
    var datetime: String,
    var thumbnail: List<String>?,
    var view_status : Boolean,
    var review_status : Boolean,
    var on_progress : Boolean,
    var refund_status : Boolean,
): Serializable