package com.weare.wearecompany.data.main.Request.deta

import java.io.Serializable

data class expertarray (
    var expert_name: String,
    var expert_type: Int,
    var room_name: String,
    var expert_image: String
) : Serializable