package com.weare.wearecompany.ui.bottommenu.main.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.data.main.data.banner
import com.weare.wearecompany.data.main.data.event
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.ui.base.BaseViewModel
import com.weare.wearecompany.utils.API
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class EventViewModel: BaseViewModel<EventNavigator>() {

    private var disposable: Disposable? = null // 1. Disposable 추가

    private var _bannerData  = MutableLiveData<ArrayList<banner>>() //값을 get/set
    val bannerData: LiveData<ArrayList<banner>> get() = _bannerData //값의 get()

    private var _eventData  = MutableLiveData<ArrayList<event>>()
    val eventData: LiveData<ArrayList<event>> get() = _eventData

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    init {
    }

    fun getEventList() {
        setIsLoading(true)

        /*val `object` = JsonObject()
        `object`.addProperty("chat_idx", ChatActivity.chat_idx)
        `object`.addProperty("msg_idx", "0")*/

        disposable = iRetrofit?.mainevent()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ respones ->
                _eventData.value = respones.event
                _bannerData.value = respones.banner
                setIsLoading(false)
                //onCleared()
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