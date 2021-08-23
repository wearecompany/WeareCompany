package com.weare.wearecompany.data.hotpick.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class room (
    val room_idx: String,
    val image: ArrayList<String>,
    val thumbnail: String,
    val name: String,
    val max_person: Int,
    val price: String,
    val time: String,
    val contents: String,
    val etc: ArrayList<String>
        ) : Parcelable