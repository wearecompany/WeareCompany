package com.weare.wearecompany.data.bottomnav.mypage.data

import java.io.Serializable

data class myreview (
    var review_idx: String,
    var user_nickname: String,
    var user_thumbnail: String,
    var grade: Int,
    var review_dt: String,
    var expert_type: Int,
    var expert_nickname: String,
    var expert_thumbnail: String,
    var expert_category: String,
    var review_contents: String,
    var review_image: String,
    var review_reply: String
) : Serializable