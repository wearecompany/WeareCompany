package com.weare.wearecompany.data.division.data

import java.io.Serializable

data class division(
    var model_idx: String,
    var name: String,
    var img: String,
    var category: String,
    var title: String,
    var view_num: Int,
    var like_num: Int,
    var grade: String,
    var generation: Int,
) : Serializable