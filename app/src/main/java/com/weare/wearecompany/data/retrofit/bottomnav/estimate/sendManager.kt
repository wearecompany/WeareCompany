package com.weare.wearecompany.data.retrofit.bottomnav.estimate

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.receive.Receivepage
import com.weare.wearecompany.data.bottomnav.estimate.send.data.SendExpertPage
import com.weare.wearecompany.data.bottomnav.estimate.send.data.SendShopPage
import com.weare.wearecompany.data.bottomnav.estimate.send.data.SendStudioPage
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.data.studio.studiopage
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.ESTIMATE
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class sendManager {

    companion object {
        val instance = sendManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun studioPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<SendStudioPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.sendstudiopage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<SendStudioPage> {
            override fun onResponse(
                call: Call<SendStudioPage>,
                response: Response<SendStudioPage>
            ) {

                var studiopage = ArrayList<SendStudioPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                SendStudioPage(
                                    reserve_idx = it.reserve_idx,
                                    expert_idx = it.expert_idx,
                                    expert_user_name = it.expert_user_name,
                                    expert_name = it.expert_name,
                                    expert_category = it.expert_category,
                                    room_image = it.room_image,
                                    room_name = it.room_name,
                                    room_maxcount = it.room_maxcount,
                                    dt_status = it.dt_status,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    time_zone = it.time_zone,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price,
                                )
                            }
                            studiopage.add(studioItem!!)
                            completion(ESTIMATE.OKAY, studiopage)
                        }
                        404 -> {
                            completion(ESTIMATE.NOT_USER, studiopage)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SendStudioPage>, t: Throwable) {

            }

        })

    }

    fun expertPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<SendExpertPage>) -> Unit
    ) {
        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.sendexpertpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<SendExpertPage> {
            override fun onResponse(
                call: Call<SendExpertPage>,
                response: Response<SendExpertPage>
            ) {
                var expertpage = ArrayList<SendExpertPage>()

                response.body().let {
                    when(response.code()) {
                        200 -> {
                            var expertItem = it?.let { it1 ->
                                SendExpertPage(
                                    reserve_idx = it1.reserve_idx,
                                    expert_idx = it1.expert_idx,
                                    expert_name = it1.expert_name,
                                    expert_category = it1.expert_category,
                                    expert_image = it1.expert_image,
                                    expert_type = it.expert_type,
                                    expert_place = it.expert_place,
                                    expert_price = it.expert_price,
                                    dt_status = it.dt_status,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    time_zone = it.time_zone,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price
                                )
                            }
                            expertpage.add(expertItem!!)
                            completion(ESTIMATE.OKAY,expertpage)
                        }
                        404 -> {
                            completion(ESTIMATE.NOT_USER, expertpage)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SendExpertPage>, t: Throwable) {

            }

        })
    }

    fun shopPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<SendShopPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.sendshoppage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<SendShopPage> {
            override fun onResponse(call: Call<SendShopPage>, response: Response<SendShopPage>) {
                var shoppage = ArrayList<SendShopPage>()
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            var shoppageItem = SendShopPage(
                                reserve_idx = it.reserve_idx,
                                expert_idx = it.expert_idx,
                                expert_user_name = it.expert_user_name,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                expert_image = it.expert_image,
                                dt_status = it.dt_status,
                                start_dt = it.start_dt,
                                end_dt = it.end_dt,
                                reserve_day = it.reserve_day,
                                reserve_contents = it.reserve_contents
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<SendShopPage>, t: Throwable) {
                Timber.d("sendManager_shopPage - onFailure() called / t: $t")

            }

        })
    }

    fun reservedelete(reserve_idx: String,completion: (ESTIMATE) -> Unit) {
        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.reservedelete(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    when (response.code()) {
                        201 -> {
                            completion(ESTIMATE.OKAY)
                        }
                    }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Timber.d("sendManager_shopPage - onFailure() called / t: $t")

            }

        })
    }

    fun requestdelete(request_idx: String,request_log_idx: String, completion: (ESTIMATE) -> Unit) {
        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)

        val call = iRetrofit?.requestdelete(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    when (response.code()) {
                        201 -> {
                            completion(ESTIMATE.OKAY)
                        }
                    }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Timber.d("sendManager_shopPage - onFailure() called / t: $t")

            }

        })
    }


}