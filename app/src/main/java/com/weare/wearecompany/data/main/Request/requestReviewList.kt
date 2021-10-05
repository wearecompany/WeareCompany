package com.weare.wearecompany.data.main.Request

import com.weare.wearecompany.data.main.Request.deta.receiveoneclick
import com.weare.wearecompany.data.main.Request.deta.reviewone
import com.weare.wearecompany.data.main.Request.deta.reviewoneclick
import java.io.Serializable

class requestReviewList (
    val reserve: ArrayList<reviewone>,
    val oneClick : ArrayList<reviewoneclick>,
    val request: Int,
    val response: Int,
    val progress: Int,
    val complete: Int,
    val review: Int,
    val refund_request: Int,
    val refund_complete: Int
) : Serializable