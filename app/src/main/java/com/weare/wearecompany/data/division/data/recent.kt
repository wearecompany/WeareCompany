package com.weare.wearecompany.data.division.data

import java.io.Serializable

data class recent(
    var model_idx: String,
    var generation: Int,
    var name: String,
    var img: String
) : Serializable