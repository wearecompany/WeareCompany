package com.weare.wearecompany.data.main.Request

import com.weare.wearecompany.data.main.Request.deta.sendone
import com.weare.wearecompany.data.main.Request.deta.sendoneclick
import java.io.Serializable

data class requestSendList (
    val reserve: ArrayList<sendone>,
    val oneClick : ArrayList<sendoneclick>,
    val request: Int,
    val response: Int,
    val progress: Int,
    val complete: Int,
    val review: Int,
    val refund_request: Int,
    val refund_complete: Int
) : Serializable
