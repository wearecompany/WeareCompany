package com.weare.wearecompany.data.retrofit.bottomnav.mypage

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.main.Request.deta.sendoneclickpage
import com.weare.wearecompany.data.main.Request.progressOneClickPage
import com.weare.wearecompany.data.main.Request.receiveOneClickPage
import com.weare.wearecompany.data.main.Request.requestSendList
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.ESTIMATE
import com.weare.wearecompany.utils.RESPONSE_STATUS
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class onclickManager {

    companion object {
        val instance = onclickManager()
    }

    val `object` = JsonObject()

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun oneclickupload(
        user_idx: String,
        expert_type: List<Int>,
        contents: String,
        price: Int,
        request_thumbnail: List<File>,
        completion: (RESPONSE_STATUS) -> Unit
    ) {

        val map: HashMap<String, RequestBody> = HashMap()

        val user_idx = user_idx?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        user_idx?.let { map.put("user_idx", it) }

        val contents_dt = contents?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        contents_dt?.let { map.put("contents", it) }

        for (i in 1..request_thumbnail.size) {
            val requesFile =
                RequestBody.create("image/png".toMediaTypeOrNull(), request_thumbnail[i - 1])
            map.put(
                "thumbnail\"; filename=\"" + "thumbnail" + i.toString(),
                requesFile
            )
        }


        val call = iRetrofit?.oneclickcall(
            params = map,
            expert_type = expert_type,
            price = price
        ).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when(response.code()) {
                    201 -> {
                        completion(RESPONSE_STATUS.OKAY)
                    }
                    204 -> {
                        completion(RESPONSE_STATUS.NOT_USER)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

        })
    }

    fun sendPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<sendoneclickpage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.sendonclickpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<sendoneclickpage> {
            override fun onResponse(
                call: Call<sendoneclickpage>,
                response: Response<sendoneclickpage>
            ) {

                var studiopage = ArrayList<sendoneclickpage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                sendoneclickpage(
                                    reserve_idx = it.reserve_idx,
                                    send_time = it.send_time,
                                    expert_type = it.expert_type,
                                    price = it.price,
                                    contents = it.contents,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<sendoneclickpage>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

        })

    }

    fun receivePage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<receiveOneClickPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.receiveonclickpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<receiveOneClickPage> {
            override fun onResponse(
                call: Call<receiveOneClickPage>,
                response: Response<receiveOneClickPage>
            ) {

                var studiopage = ArrayList<receiveOneClickPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                receiveOneClickPage(
                                    reserve_idx = it.reserve_idx,
                                    expert_array = it.expert_array,
                                    reserve_name = it.reserve_name,
                                    date = it.date,
                                    time = it.time,
                                    timezone = it.timezone,
                                    contents = it.contents,
                                    price = it.price,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<receiveOneClickPage>, t: Throwable) {

            }

        })

    }

    fun progressPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<progressOneClickPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.progressonclickList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<progressOneClickPage> {
            override fun onResponse(
                call: Call<progressOneClickPage>,
                response: Response<progressOneClickPage>
            ) {

                var studiopage = ArrayList<progressOneClickPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                progressOneClickPage(
                                    reserve_idx = it.reserve_idx,
                                    reserve_status = it.reserve_status,
                                    reserve_tid = it.reserve_tid,
                                    bill_method = it.bill_method,
                                    bill_date = it.bill_date,
                                    refund_money = it.refund_money,
                                    refund_status = it.refund_status,
                                    expert_array = it.expert_array,
                                    reserve_name = it.reserve_name,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    reserve_time_term = it.reserve_time_term,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<progressOneClickPage>, t: Throwable) {

            }

        })

    }

    fun progressOkPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<progressOneClickPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.progressokoneclickpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<progressOneClickPage> {
            override fun onResponse(
                call: Call<progressOneClickPage>,
                response: Response<progressOneClickPage>
            ) {

                var studiopage = ArrayList<progressOneClickPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                progressOneClickPage(
                                    reserve_idx = it.reserve_idx,
                                    reserve_status = it.reserve_status,
                                    reserve_tid = it.reserve_tid,
                                    bill_method = it.bill_method,
                                    bill_date = it.bill_date,
                                    refund_money = it.refund_money,
                                    refund_status = it.refund_status,
                                    expert_array = it.expert_array,
                                    reserve_name = it.reserve_name,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    reserve_time_term = it.reserve_time_term,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<progressOneClickPage>, t: Throwable) {

            }

        })

    }

    fun reviewPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<progressOneClickPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.reviewoneclickpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<progressOneClickPage> {
            override fun onResponse(
                call: Call<progressOneClickPage>,
                response: Response<progressOneClickPage>
            ) {

                var studiopage = ArrayList<progressOneClickPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                progressOneClickPage(
                                    reserve_idx = it.reserve_idx,
                                    reserve_status = it.reserve_status,
                                    reserve_tid = it.reserve_tid,
                                    bill_method = it.bill_method,
                                    bill_date = it.bill_date,
                                    refund_money = it.refund_money,
                                    refund_status = it.refund_status,
                                    expert_array = it.expert_array,
                                    reserve_name = it.reserve_name,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    reserve_time_term = it.reserve_time_term,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<progressOneClickPage>, t: Throwable) {

            }

        })

    }

    fun refundPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<progressOneClickPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.refundoneclickpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<progressOneClickPage> {
            override fun onResponse(
                call: Call<progressOneClickPage>,
                response: Response<progressOneClickPage>
            ) {

                var studiopage = ArrayList<progressOneClickPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                progressOneClickPage(
                                    reserve_idx = it.reserve_idx,
                                    reserve_status = it.reserve_status,
                                    reserve_tid = it.reserve_tid,
                                    bill_method = it.bill_method,
                                    bill_date = it.bill_date,
                                    refund_money = it.refund_money,
                                    refund_status = it.refund_status,
                                    expert_array = it.expert_array,
                                    reserve_name = it.reserve_name,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    reserve_time_term = it.reserve_time_term,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<progressOneClickPage>, t: Throwable) {

            }

        })

    }

    fun refundokPage(
        reserve_idx: String,
        completion: (ESTIMATE, ArrayList<progressOneClickPage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)

        val call = iRetrofit?.refundokoneclickpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<progressOneClickPage> {
            override fun onResponse(
                call: Call<progressOneClickPage>,
                response: Response<progressOneClickPage>
            ) {

                var studiopage = ArrayList<progressOneClickPage>()

                response.body().let {
                    when (response.code()) {
                        200 -> {
                            var studioItem = it?.let { it1 ->
                                progressOneClickPage(
                                    reserve_idx = it.reserve_idx,
                                    reserve_status = it.reserve_status,
                                    reserve_tid = it.reserve_tid,
                                    bill_method = it.bill_method,
                                    bill_date = it.bill_date,
                                    refund_money = it.refund_money,
                                    refund_status = it.refund_status,
                                    expert_array = it.expert_array,
                                    reserve_name = it.reserve_name,
                                    reserve_dt = it.reserve_dt,
                                    reserve_time = it.reserve_time,
                                    reserve_time_term = it.reserve_time_term,
                                    reserve_headcount = it.reserve_headcount,
                                    reserve_contents = it.reserve_contents,
                                    reserve_price = it.reserve_price,
                                    thumbnail = it.thumbnail
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

            override fun onFailure(call: Call<progressOneClickPage>, t: Throwable) {

            }

        })

    }
}