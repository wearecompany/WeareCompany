package com.weare.wearecompany.ui.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.weare.wearecompany.utils.kakao.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val mSchedulerProvider: SchedulerProvider? = null
    private val mIsLoading = ObservableBoolean(false)

    /**
     * RxJava 의 observing을 위한 부분.
     * addDisposable을 이용하여 추가하기만 하면 된다
     */

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    open fun getCompositeDisposable(): CompositeDisposable? {
        return compositeDisposable
    }

    open fun getIsLoading(): ObservableBoolean? {
        return mIsLoading
    }

    open fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    open fun getSchedulerProvider(): SchedulerProvider? {
        return mSchedulerProvider
    }

}