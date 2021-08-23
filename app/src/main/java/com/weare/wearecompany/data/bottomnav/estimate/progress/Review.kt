package com.weare.wearecompany.data.bottomnav.estimate.progress

import java.io.Serializable

data class Review (
    val expert_type: Int,
    val expert_idx: String,
    val expert_user_idx: String,
    val expert_user_nickname: String,
    val expert_category: String,
    val expert_title: String,
    val expert_thumbnail: String,
): Serializable