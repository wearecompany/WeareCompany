package com.weare.wearecompany.data.retrofit.bottomnav.estimate

import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveShopPage
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.ESTIMATE
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class refundManager {

    companion object {
        val instance = refundManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun refundreserve(
        reserve_idx: String,
        refund_contents: String,
        completion: (ESTIMATE) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)
        `object`.addProperty("refund_contents", refund_contents)

        val call = iRetrofit?.refundreserve(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                response.body()?.let {
                    when(response.code()) {
                        201 -> {


                            completion(ESTIMATE.OKAY)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

    fun refundrequest(
        request_idx: String,
        request_log_idx: String,
        refund_contents: String,
        completion: (ESTIMATE) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)
        `object`.addProperty("refund_contents", refund_contents)

        val call = iRetrofit?.refundrequest(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                response.body()?.let {
                    when(response.code()) {
                        201 -> {
                            completion(ESTIMATE.OKAY)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }
}