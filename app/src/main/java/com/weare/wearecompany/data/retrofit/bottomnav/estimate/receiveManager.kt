package com.weare.wearecompany.data.retrofit.bottomnav.estimate

import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveExpertPage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveShopPage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveStudioPage
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.ESTIMATE
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class receiveManager {

    companion object {
        val instance = receiveManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun studioPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<ReceiveStudioPage>) -> Unit) {

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
                var shoppage = ArrayList<ReceiveStudioPage>()
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            var shoppageItem = ReceiveStudioPage(
                                reserve_idx = it.reserve_idx,
                                expert_idx = it.expert_idx,
                                expert_user_name = it.expert_user_name,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                room_image = it.room_image,
                                room_name = it.room_name,
                                reserve_dt = it.reserve_dt,
                                reserve_time = it.reserve_time,
                                reserve_time_term = it.reserve_time_term,
                                reserve_headcount = it.reserve_headcount,
                                reserve_contents = it.reserve_contents,
                                reserve_add_contents = it.reserve_add_contents,
                                reserve_price = it.reserve_price
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                        404 -> {
                            completion(ESTIMATE.NOT_USER, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ReceiveStudioPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

    fun expertPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<ReceiveExpertPage>) -> Unit) {

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
                var shoppage = ArrayList<ReceiveExpertPage>()
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            var shoppageItem = ReceiveExpertPage(
                                reserve_idx = it.reserve_idx,
                                expert_idx = it.expert_idx,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                expert_image = it.expert_image,
                                expert_type = it.expert_type,
                                expert_price = it.expert_price,
                                expert_place = it.expert_place,
                                reserve_dt = it.reserve_dt,
                                reserve_time = it.reserve_time,
                                reserve_time_term = it.reserve_time_term,
                                reserve_headcount = it.reserve_headcount,
                                reserve_contents = it.reserve_contents,
                                reserve_add_contents = it.reserve_add_contents,
                                reserve_price = it.reserve_price
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                        404 -> {
                            completion(ESTIMATE.NOT_USER, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ReceiveExpertPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

    fun shopPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<ReceiveShopPage>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.receiveshoppage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ReceiveShopPage> {
            override fun onResponse(
                call: Call<ReceiveShopPage>,
                response: Response<ReceiveShopPage>
            ) {
                var shoppage = ArrayList<ReceiveShopPage>()
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            var shoppageItem = ReceiveShopPage(
                                reserve_idx = it.reserve_idx,
                                expert_idx = it.expert_idx,
                                expert_user_name = it.expert_user_name,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                expert_image = it.expert_image,
                                start_dt = it.start_dt,
                                end_dt = it.end_dt,
                                reserve_time_term = it.reserve_time_term,
                                reserve_contents = it.reserve_contents,
                                reserve_add_contents = it.reserve_add_contents,
                                reserve_final_price = it.reserve_final_price
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ReceiveShopPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

}