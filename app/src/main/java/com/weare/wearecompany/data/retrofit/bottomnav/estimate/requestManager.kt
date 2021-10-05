package com.weare.wearecompany.data.retrofit.bottomnav.estimate

import android.util.Log
import com.google.gson.JsonElement
import com.weare.wearecompany.data.bottomnav.estimate.send.Sendpage
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.data.uploadRequest
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveList
import com.weare.wearecompany.data.bottomnav.estimate.receive.Receivepage
import com.weare.wearecompany.data.bottomnav.estimate.send.SendList
import com.weare.wearecompany.utils.ESTIMATE
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.io.File

class requestManager {

    companion object {
        val instance = requestManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //보낸요청 리스트
    fun send_list(
        user_idx: String,
        completion: (RESPONSE_STATUS, ArrayList<SendList>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.sendlist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<SendList> {
            override fun onResponse(call: Call<SendList>, response: Response<SendList>) {
                response.body()?.let {
                    var requsetlistArray = ArrayList<SendList>()
                    when(response.code()) {
                        200 -> {
                            val requsetlistItem = SendList(
                                reserve = it.reserve,
                                request = it.request
                            )
                            requsetlistArray.add(requsetlistItem)
                            completion(RESPONSE_STATUS.OKAY, requsetlistArray)
                        }
                        404 -> {

                        }
                    }
                }
            }

            override fun onFailure(call: Call<SendList>, t: Throwable) {
                Log.d(Constants.TAG, "requestManager_request_list - onFailure() called / t: $t")
            }

        })
    }

    //받은 견적 리스트
    fun receive_list(
        user_idx: String,
        completion: (ESTIMATE, ArrayList<ReceiveList>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.receivelist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ReceiveList> {
            override fun onResponse(call: Call<ReceiveList>, response: Response<ReceiveList>) {
                response.body()?.let {
                    var receivelistArray = ArrayList<ReceiveList>()
                        when(response.code()) {
                            200 -> {
                                val receivelistItem = ReceiveList(
                                    reserve = it.reserve,
                                    request = it.request
                                )
                                receivelistArray.add(receivelistItem)
                                completion(ESTIMATE.OKAY,receivelistArray)
                            }
                            404 -> {

                            }
                        }
                }
            }

            override fun onFailure(call: Call<ReceiveList>, t: Throwable) {
                Timber.d("requestManager_request_list - onFailure() called / t: $t")
            }

        })
    }

}
