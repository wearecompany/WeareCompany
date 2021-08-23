package com.weare.wearecompany.data.bottomnav.estimate.receive

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReceiveList (
    var reserve: List<ReceiveReserve>?,
    var request: List<ReceiveResponse>?
) : Parcelable