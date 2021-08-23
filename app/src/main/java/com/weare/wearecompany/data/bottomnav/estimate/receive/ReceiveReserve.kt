package com.weare.wearecompany.data.bottomnav.estimate.receive

import com.weare.wearecompany.data.bottomnav.estimate.receive.data.*
import java.io.Serializable

data class ReceiveReserve (
    val studio: receiveStudio,
    val expert: receiveExpert,
    val shop: receiveShop,
): Serializable