package com.weare.wearecompany.data.bottomnav.estimate.send

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class SendRequest(
    var request_idx: String,
    var send_time: String,
    var main_category: String,
    var sub_category: String,
    var user_nickname: String,
    var user_thumbnail: String,
    var date_status: String,
    var datetime: String,
    var thumbnail: List<String>
) : Parcelable