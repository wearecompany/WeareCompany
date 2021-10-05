package com.weare.wearecompany.data.division

import com.weare.wearecompany.data.division.data.qa
import java.io.Serializable

data class DivisionPage (
    var model_idx: String,
    var thumbnail: String,
    var model_img: String,
    var model_title: String,
    var view_num: Int,
    var like_num: Int,
    var model_name: String,
    var model_category: String,
    var video_thumbnail: String,
    var video_link: String,
    var hashtag: String,
    var title: String,
    var scrap_status: Boolean,
    var qa: ArrayList<qa>,
) : Serializable