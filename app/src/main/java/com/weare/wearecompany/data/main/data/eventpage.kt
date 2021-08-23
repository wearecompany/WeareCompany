package com.weare.wearecompany.data.main.data

import java.io.Serializable

data class eventpage(
    var event_idx: String,
    var event_title: String,
    var event_sub_title: String,
    var event_datetime: String,
    var event_contents: String,
    var event_image: String,
    var event_type: String,
) : Serializable