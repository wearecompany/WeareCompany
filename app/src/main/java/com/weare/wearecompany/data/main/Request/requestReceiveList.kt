package com.weare.wearecompany.data.main.Request

import com.weare.wearecompany.data.main.Request.deta.receiveoneclick
import com.weare.wearecompany.data.main.Request.deta.sendone
import java.io.Serializable

class requestReceiveList (
    val reserve: ArrayList<sendone>,
    val oneClick : ArrayList<receiveoneclick>,
    val request: Int,
    val response: Int,
    val progress: Int,
    val complete: Int,
    val review: Int,
    val refund_request: Int,
    val refund_complete: Int
) : Serializable