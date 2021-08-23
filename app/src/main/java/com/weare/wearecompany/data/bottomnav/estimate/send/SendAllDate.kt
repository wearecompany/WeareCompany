package com.weare.wearecompany.data.bottomnav.estimate.send

import java.io.Serializable

data class SendAllDate (
    var type : Int,
    var reserve_idx: String,
    var request_idx: String,
    var send_time: String,
    var room_name: String,
    var head_count: String,
    var expert_type: Int?,
    var date_status: Boolean?,
    var price: Int?,
    var time: String,
    var date: String,
    var date_term: String,
    var start_date: String,
    var end_date: String,
    var main_category: String,
    var sub_category: String,
    var user_nickname: String,
    var user_thumbnail: String,
    var datetime: String,
    var thumbnail: List<String>?,
): Serializable