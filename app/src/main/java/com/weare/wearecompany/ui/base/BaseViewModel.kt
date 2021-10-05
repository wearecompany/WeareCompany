package com.weare.wearecompany.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.weare.wearecompany.utils.kakao.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {


    lateinit var mSchedulerProvider: SchedulerProvider
    private val mIsLoading = ObservableBoolean(false)
    lateinit var mCompositeDisposable: CompositeDisposable
    private var mNavigator: WeakReference<N>? = null

    /**
     * RxJava 의 observing을 위한 부분.
     * addDisposable을 이용하여 추가하기만 하면 된다
     */

    init {

    }
    fun BaseViewModel(schedulerProvider: SchedulerProvider) {
        mSchedulerProvider = schedulerProvider
        this.mCompositeDisposable = CompositeDisposable()
    }

    //private val compositeDisposable = CompositeDisposable()

    /*override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }*/

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
    open fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    open fun getIsLoading(): ObservableBoolean? {
        return mIsLoading
    }

    open fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    open fun getNavigator(): N? {
        return mNavigator?.get()
    }

    open fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

    open fun getSchedulerProvider(): SchedulerProvider {
        return mSchedulerProvider
    }



}