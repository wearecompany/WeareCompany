package com.weare.wearecompany.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weare.wearecompany.ui.bottommenu.main.event.EventViewModel
import com.weare.wearecompany.ui.bottommenu.main.weekly.WeeklyViewModel
import com.weare.wearecompany.utils.kakao.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelProviderFactory : ViewModelProvider.NewInstanceFactory() {

    private var schedulerProvider: SchedulerProvider? = null
    private var context: Context? = null

    /*@Inject //파라미터를 자동으로 찾아주는 것
    fun ViewModelProviderFactory(
        schedulerProvider: SchedulerProvider?, context: Context,
    ) {

        this.schedulerProvider = schedulerProvider
        this.context = context

    }*/

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeeklyViewModel::class.java)) {
            return WeeklyViewModel() as T
        } else if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}