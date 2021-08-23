package com.weare.wearecompany.data.bottomnav.estimate.send

import java.io.Serializable

data class Sendpage (
    val request_idx: String,
    val expert_type: String,
    val expert_category: String,
    val user_nickname: String,
    val user_thumbnail: String,
    val datetime: String,
    val contents: String,
    val thumbnail: List<String>,
) : Serializable