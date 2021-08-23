package com.weare.wearecompany.data.chatting

import java.io.Serializable

data class ChatDatail (
    var chat_idx : String,
    var user_idx:String,
    var expert_user_idx:String,
    var oppenent_nickname: String,
    var expert_type: String,
    var expert_idx: String,
    var room_id: String,
    var report_status: Int,
): Serializable