package com.weare.wearecompany.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weare.wearecompany.utils.kakao.rx.SchedulerProvider

class ViewModelProviderFactory : ViewModelProvider.NewInstanceFactory() {

    private val schedulerProvider: SchedulerProvider? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        /*if (modelClass.isAssignableFrom(loginViewModel::class.java)) {
            return loginViewModel(dataManager, schedulerProvider) as T
        } else if (modelClass.isAssignableFrom(loginToViewModel::class.java)) {
            return loginToViewModel() as T
        }*/
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}