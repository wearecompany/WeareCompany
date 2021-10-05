package com.weare.wearecompany.data.main.Request

import com.weare.wearecompany.data.main.Request.deta.top
import java.io.Serializable

data class TopList (
    val top: ArrayList<top>,
) : Serializable