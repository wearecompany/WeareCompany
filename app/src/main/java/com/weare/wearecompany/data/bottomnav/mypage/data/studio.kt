package com.weare.wearecompany.data.bottomnav.mypage.data

import java.io.Serializable

data class studio (
    var idx: String,
    var image: String,
    var name: String,
    var category: String,
    var description: String,
) : Serializable