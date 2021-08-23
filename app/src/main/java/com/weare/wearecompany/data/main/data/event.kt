package com.weare.wearecompany.data.main.data

import java.io.Serializable

data class event(
    var idx: String?,
    var title: String?,
    var sub: String?,
    var image: String?,
    var time: String?,
    var goods: String?,
    var person: String?,
    var event_type: Int
) : Serializable