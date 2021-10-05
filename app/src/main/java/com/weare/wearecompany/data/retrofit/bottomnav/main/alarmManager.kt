package com.weare.wearecompany.data.retrofit.bottomnav.main

import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressExpertPage
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressManyPage
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressStudioPage
import com.weare.wearecompany.data.bottomnav.estimate.receive.Receivepage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveExpertPage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveStudioPage
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.ALARM
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.ESTIMATE
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class alarmManager {

    companion object {
        val instance = alarmManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun receivestudio(
        reserve_idx: String,
        completion: (ALARM) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.receivestudiopage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ReceiveStudioPage> {
            override fun onResponse(
                call: Call<ReceiveStudioPage>,
                response: Response<ReceiveStudioPage>
            ) {

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            completion(ALARM.OKAY)
                        }
                        404 -> {
                            completion(ALARM.NOT_CONTENT)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ReceiveStudioPage>, t: Throwable) {

            }

        })

    }

    fun receiveexpert(
        reserve_idx: String,
        completion: (ALARM) -> Unit
    ) {
        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.receiveexpertpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ReceiveExpertPage> {
            override fun onResponse(
                call: Call<ReceiveExpertPage>,
                response: Response<ReceiveExpertPage>
            ) {

                response.body().let {
                    when(response.code()) {
                        200 -> {
                            completion(ALARM.OKAY)
                        }
                        404 -> {
                            completion(ALARM.NOT_CONTENT)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ReceiveExpertPage>, t: Throwable) {

            }

        })
    }

    fun refundstudio(
        reserve_idx: String,
        completion: (ALARM) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.progressstudiopage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ProgressStudioPage> {
            override fun onResponse(
                call: Call<ProgressStudioPage>,
                response: Response<ProgressStudioPage>
            ) {
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            completion(ALARM.OKAY)
                        }
                        404 -> {
                            completion(ALARM.NOT_CONTENT)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ProgressStudioPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

    fun refundexpert(
        reserve_idx: String,
        completion: (ALARM) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.progressexpertpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ProgressExpertPage> {
            override fun onResponse(
                call: Call<ProgressExpertPage>,
                response: Response<ProgressExpertPage>
            ) {
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            completion(ALARM.OKAY)
                        }
                        404 -> {
                            completion(ALARM.NOT_CONTENT)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ProgressExpertPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }
}