package com.weare.wearecompany.data.bottomnav.estimate.send


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class SendList (
    var reserve: List<SendReserve>?,
    var request: List<SendRequest>?
) : Parcelable