package com.weare.wearecompany.data.main.Request

import com.weare.wearecompany.data.main.Request.deta.expertarray
import java.io.Serializable

data class receiveOneClickPage (
    var reserve_idx: String,
    var expert_array : ArrayList<expertarray>,
    var reserve_name: String,
    var date: String,
    var time: String,
    var timezone: String,
    var contents : String,
    var price : Int,
    var thumbnail : ArrayList<String>
) : Serializable