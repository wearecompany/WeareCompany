package com.weare.wearecompany.utils.kakao.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun computation(): Scheduler? {
        return Schedulers.computation()
    }

    fun io(): Scheduler?{
        return Schedulers.io()
    }

    fun ui(): Scheduler? {
        return AndroidSchedulers.mainThread()
    }
}