package com.weare.wearecompany.data.notification.data

import java.io.Serializable

data class alarm (
    var idx : String,
    var type: Int,
    var expert_type: Int,
    var target_idx: String,
    var title: String,
    var contents: String,
    var date_diff: String,
    var view_status: Int
): Serializable