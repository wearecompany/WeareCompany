package com.weare.wearecompany.data.bottomnav.estimate.receive.data

import java.io.Serializable

data class receiveShop (
    val reserve_idx: String,
    val price: Int,
    val date_term: String,
    val start_date: String,
    val end_date: String,
): Serializable