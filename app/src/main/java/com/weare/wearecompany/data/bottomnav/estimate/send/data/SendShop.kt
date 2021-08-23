package com.weare.wearecompany.data.bottomnav.estimate.send.data

import java.io.Serializable

data class sendShop (
    var reserve_idx: String,
    var date_status: Boolean,
    var date_term: String,
    var start_date: String,
    var end_date: String
): Serializable