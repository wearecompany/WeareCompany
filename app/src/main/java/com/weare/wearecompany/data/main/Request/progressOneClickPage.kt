package com.weare.wearecompany.data.main.Request

import com.weare.wearecompany.data.main.Request.deta.expertarray
import java.io.Serializable

data class progressOneClickPage (
    var reserve_idx: String,
    var reserve_status: Int,
    var reserve_tid: String,
    var bill_method: String,
    var bill_date: String,
    var refund_money: Int,
    var refund_status: Int,
    var expert_array : ArrayList<expertarray>,
    var reserve_name: String,
    var reserve_dt: String,
    var reserve_time: Int,
    var reserve_time_term: String,
    var reserve_headcount: Int,
    var reserve_contents : String,
    var reserve_price : Int,
    var thumbnail : ArrayList<String>
) : Serializable