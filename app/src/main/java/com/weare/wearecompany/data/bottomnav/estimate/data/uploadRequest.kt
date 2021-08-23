package com.weare.wearecompany.data.bottomnav.estimate.data

import java.io.File
import java.io.Serializable

data class uploadRequest (
    var user_idx: String,
    var expert_type: Int,
    var expert_category: Int,
    var place: Int,
    var request_thumbnail : List<File>,
    var request_dt_status: Boolean,
    var request_dt: String,
    var request_start_time: String,
    var request_end_time: String,
    var request_contents: String,
) : Serializable