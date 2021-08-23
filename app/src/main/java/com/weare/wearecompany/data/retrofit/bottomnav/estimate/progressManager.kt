package com.weare.wearecompany.data.retrofit.bottomnav.estimate

import android.util.Log
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressList
import com.weare.wearecompany.data.bottomnav.estimate.progress.Review
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.*
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.ESTIMATE
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class progressManager {

    companion object {
        val instance = progressManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun progress_list(
        type:Int,
        page:Int,
        user_idx: String,
        completion: (ESTIMATE, ArrayList<ProgressList>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("type", type)
        `object`.addProperty("page", page)
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.progresslist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ProgressList> {
            override fun onResponse(call: Call<ProgressList>, response: Response<ProgressList>) {
                response.body()?.let {
                    var progresslist = ArrayList<ProgressList>()
                    when (response.code()) {
                        200 -> {
                            val progresslistItem = ProgressList(
                                progress = it.progress,
                            )
                            progresslist.add(progresslistItem)
                            completion(ESTIMATE.OKAY, progresslist)
                        }
                        404 -> {

                        }
                    }
                }
            }

            override fun onFailure(call: Call<ProgressList>, t: Throwable) {
                Log.d(Constants.TAG, "progressManager_progress_list - onFailure() called / t: $t")
            }

        })
    }

    fun studioPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<ProgressStudioPage>) -> Unit
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
                var shoppage = ArrayList<ProgressStudioPage>()
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            var shoppageItem = ProgressStudioPage(
                                reserve_idx = it.reserve_idx,
                                reserve_tid = it.reserve_tid,
                                expert_idx = it.expert_idx,
                                expert_user_name = it.expert_user_name,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                room_image = it.room_image,
                                complete = it.complete,
                                bill_method = it.bill_method,
                                bill_date = it.bill_date,
                                refund_money = it.refund_money,
                                refund_status = it.refund_status,
                                room_name = it.room_name,
                                reserve_dt = it.reserve_dt,
                                reserve_time = it.reserve_time,
                                reserve_time_term = it.reserve_time_term,
                                reserve_headcount = it.reserve_headcount,
                                reserve_contents = it.reserve_contents,
                                reserve_add_contents = it.reserve_add_contents,
                                reserve_price = it.reserve_price,
                                reserve_add_price = it.reserve_add_price,
                                reserve_add_price_contents = it.reserve_add_price_contents,
                                reserve_final_price = it.reserve_final_price
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ProgressStudioPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

    fun expertPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<ProgressExpertPage>) -> Unit
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
                var shoppage = ArrayList<ProgressExpertPage>()
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            var shoppageItem = ProgressExpertPage(
                                reserve_idx = it.reserve_idx,
                                reserve_tid = it.reserve_tid,
                                expert_idx = it.expert_idx,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                expert_image = it.expert_image,
                                expert_price = it.expert_price,
                                expert_place = it.expert_place,
                                complete = it.complete,
                                bill_method = it.bill_method,
                                bill_date = it.bill_date,
                                refund_money = it.refund_money,
                                refund_status = it.refund_status,
                                expert_type = it.expert_type,
                                reserve_dt = it.reserve_dt,
                                reserve_time = it.reserve_time,
                                reserve_time_term = it.reserve_time_term,
                                reserve_headcount = it.reserve_headcount,
                                reserve_contents = it.reserve_contents,
                                reserve_add_contents = it.reserve_add_contents,
                                reserve_price = it.reserve_price,
                                reserve_add_price = it.reserve_add_price,
                                reserve_add_price_contents = it.reserve_add_price_contents,
                                reserve_final_price = it.reserve_final_price
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ProgressExpertPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }


    fun shopPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<ProgressShopPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.progressshoppage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ProgressShopPage> {
            override fun onResponse(
                call: Call<ProgressShopPage>,
                response: Response<ProgressShopPage>
            ) {
                var shoppage = ArrayList<ProgressShopPage>()
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            var shoppageItem = ProgressShopPage(
                                reserve_idx = it.reserve_idx,
                                reserve_tid = it.reserve_tid,
                                expert_idx = it.expert_idx,
                                expert_user_name = it.expert_user_name,
                                expert_name = it.expert_name,
                                expert_category = it.expert_category,
                                expert_image = it.expert_image,
                                complete = it.complete,
                                bill_method = it.bill_method,
                                bill_date = it.bill_date,
                                refund_money = it.refund_money,
                                refund_status = it.refund_status,
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

            override fun onFailure(call: Call<ProgressShopPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }

    fun manyPage(
        request_idx: String,
        request_log_idx: String,
        completion: (ESTIMATE, ArrayList<ProgressManyPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)

        val call = iRetrofit?.progressmanypage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ProgressManyPage> {
            override fun onResponse(
                call: Call<ProgressManyPage>,
                response: Response<ProgressManyPage>
            ) {
                var shoppage = ArrayList<ProgressManyPage>()
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            var shoppageItem = ProgressManyPage(
                                request_idx = it.request_idx,
                                request_log_idx = it.request_log_idx,
                                expert_idx = it.expert_idx,
                                expert_name = it.expert_name,
                                expert_image = it.expert_image,
                                expert_type = it.expert_type,
                                expert_category = it.expert_category,
                                expert_price = it.expert_price,
                                expert_place = it.expert_place,
                                complete = it.complete,
                                reserve_tid = it.reserve_tid,
                                bill_method = it.bill_method,
                                bill_date = it.bill_date,
                                refund_money = it.refund_money,
                                refund_status = it.refund_status,
                                category = it.category,
                                price = it.price,
                                user_nickname = it.user_nickname,
                                user_thumbnail = it.user_thumbnail,
                                datetime = it.datetime,
                                request_contents = it.request_contents,
                                response_contents = it.response_contents,
                                thumbnail = it.thumbnail
                            )
                            shoppage.add(shoppageItem)
                            completion(ESTIMATE.OKAY, shoppage)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ProgressManyPage>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }



    fun reserve_complete(
        reserve_idx: String,
        completion: (ESTIMATE) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.progressreservecomplete(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                response.body()?.let {
                    when(response.code()) {
                        201 -> {
                            completion(ESTIMATE.OKAY)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

        })
    }

    fun request_complete(
        request_idx: String,
        request_log_idx: String,
        completion: (ESTIMATE) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)

        val call = iRetrofit?.progressrequestcomplete(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                response.body()?.let {
                    when(response.code()) {
                        201 -> {
                            completion(ESTIMATE.OKAY)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

        })
    }

    fun review(type: Int,
               reserve_idx: String,
               request_idx: String,
               request_log_idx: String,
               completion: (ESTIMATE, ArrayList<Review>) -> Unit) {

        iRetrofit?.reviewcall(type,reserve_idx,request_idx,request_log_idx)?.enqueue(object : retrofit2.Callback<Review> {
            override fun onResponse(
                call: Call<Review>,
                response: Response<Review>
            ) {
                val reviewArray = ArrayList<Review>()
                response.body()?.let {

                    val reviewItem = Review(
                        expert_type = it.expert_type,
                        expert_idx = it.expert_idx,
                        expert_user_idx = it.expert_user_idx,
                        expert_user_nickname = it.expert_user_nickname,
                        expert_category = it.expert_category,
                        expert_title = it.expert_title,
                        expert_thumbnail = it.expert_thumbnail,
                    )
                    reviewArray.add(reviewItem)
                    completion(ESTIMATE.OKAY,reviewArray)
                }
            }

            override fun onFailure(call: Call<Review>, t: Throwable) {
                Log.d(Constants.TAG, "MemberManager_edit_stdio - onFailure() called / t: $t")
            }

        })
    }
}