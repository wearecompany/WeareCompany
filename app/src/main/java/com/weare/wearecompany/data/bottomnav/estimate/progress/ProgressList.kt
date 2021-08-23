package com.weare.wearecompany.data.bottomnav.estimate.progress

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProgressList (
    var progress: List<ProgressReserve>,
) : Parcelable