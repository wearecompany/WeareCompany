package com.weare.wearecompany.data.chatting.data

import java.io.Serializable

data class chatlist (
    var chat_idx : String,
    var type: Int,
    var reserve_idx: String,
    var request_idx: String,
    var request_log_idx: String,
    var user_idx: String,
    var expert_user_idx: String,
    var expert_user_profile: String,
    var expert_user_nickname: String,
    var expert_category: String,
    var last_chat_msg: String,
    var last_chat_dt: String,
    var expert_type: Int,
    var status: Int,
    var report_status: Int,
    var price: Int,
    var refund_status: Int,
    var chat_end: Int,
): Serializable