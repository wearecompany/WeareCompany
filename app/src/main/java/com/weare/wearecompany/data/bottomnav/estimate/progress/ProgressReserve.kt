package com.weare.wearecompany.data.bottomnav.estimate.progress

import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressExpert
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressMany
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressStudio
import java.io.Serializable

data class ProgressReserve (
    val studio: ProgressStudio,
    val expert: ProgressExpert,
    val request: ProgressMany
): Serializable