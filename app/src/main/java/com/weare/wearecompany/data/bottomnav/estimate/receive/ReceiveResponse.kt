package com.weare.wearecompany.data.bottomnav.estimate.receive

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReceiveResponse (
    var request_idx: String,
    var request_log_idx: String,
    var main_category: String,
    var sub_category: String,
    var user_nickname: String,
    var user_thumbnail: String,
    var price: Int,
    var datetime: String,
    var thumbnail: List<String>
) : Parcelable