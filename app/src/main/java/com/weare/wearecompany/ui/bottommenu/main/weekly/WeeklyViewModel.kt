package com.weare.wearecompany.ui.bottommenu.main.weekly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.ui.base.BaseViewModel
import com.weare.wearecompany.utils.API
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class WeeklyViewModel : BaseViewModel<WeeklyNavigator>() {

    private var _liveData  = MutableLiveData<ArrayList<hotpick>>()
    val liveData: LiveData<ArrayList<hotpick>> get() = _liveData

    private var disposable: Disposable? = null // 1. Disposable 추가

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    init {
        getWeeklyList()
    }


    fun getWeeklyList() {
        setIsLoading(true)

        /*val `object` = JsonObject()
        `object`.addProperty("chat_idx", ChatActivity.chat_idx)
        `object`.addProperty("msg_idx", "0")*/

        disposable = iRetrofit?.mainhotpick()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ respones ->
                _liveData.value = respones.hotpick
                setIsLoading(false)
            }, {
                setIsLoading(false)
            })
    }

    override fun onCleared() {    // 3. onCleared() override
        super.onCleared()
        if (disposable?.isDisposed == false) { // 4. onCleared() 될 때 dispose() 호출
            disposable?.dispose()
        }
    }

}