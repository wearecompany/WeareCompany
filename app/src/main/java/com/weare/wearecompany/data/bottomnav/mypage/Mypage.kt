package com.weare.wearecompany.data.bottomnav.mypage

import java.io.Serializable

data class Mypage(
    val profile_image: String,
    val nickname: String,
    val like_num: Int,
    val review_num: Int,
    val review_status: Int,
    val request: Int,
    val response: Int,
    val progress: Int,
    val complete: Int,
    val review: Int,
    val refund_request: Int,
    val refund_complete: Int
) : Serializable