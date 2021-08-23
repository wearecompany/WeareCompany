package com.weare.wearecompany.data.chatting

import java.io.Serializable

data class Detail (
    var chat_idx : String,
    var type: Int,
    var user_idx:String,
    var expert_user_idx:String,
    var opponent_nickname: String,
    var reserve_idx: String,
    var request_idx: String,
    var request_log_idx: String,
    var expert_type: Int,
    var status: Int,
    var report_status: Int,
    var refund_status: Int,
    var chat_end: Int
): Serializable