package com.weare.wearecompany.data.main.data

import java.io.Serializable

data class hotpick(
    var idx: String?,
    var target_type: Int?,
    var target_idx: String?,
    var name: String?,
    var title: String?,
    var place: String?,
    var category: String?,
    var image: String?
) : Serializable