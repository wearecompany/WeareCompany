package com.weare.wearecompany.data.main.data

import java.io.Serializable

data class list(
    var idx: String,
    var expert_image: String,
    var expert_name: String,
    var expert_title: String,
    var expert_place: String,
    var expert_category: String
) : Serializable