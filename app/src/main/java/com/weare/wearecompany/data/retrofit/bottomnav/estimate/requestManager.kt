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

    fun upload(
        user_idx: String?,
        expert_type: Int?,
        expert_category: Int?,
        place: Int?,
        request_thumbnail: List<File>,
        request_dt_status: Boolean?,
        request_dt: String?,
        request_start_time: String?,
        request_end_time: String,
        request_contents: String,
        completion: (RESPONSE_STATUS) -> Unit
    ) {

        val map: HashMap<String, RequestBody> = HashMap()

        val user_idx = user_idx?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        user_idx?.let { map.put("user_idx", it) }
        val request_dt =
            request_dt?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        request_dt?.let { map.put("request_dt", it) }
        val request_start_time =
            request_start_time?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        request_start_time?.let { map.put("request_start_time", it) }
        val request_end_time =
            RequestBody.create("text/plain".toMediaTypeOrNull(), request_end_time)
        map.put("request_end_time", request_end_time)
        val request_contents = RequestBody.create("text/plain".toMediaTypeOrNull(), request_contents)
        map.put("request_contents", request_contents)

        for (i in 1..request_thumbnail.size) {
            val requesFile =
                RequestBody.create("image/png".toMediaTypeOrNull(), request_thumbnail[i - 1])
            map.put(
                "request_thumbnail\"; filename=\"" + "request_thumbnail" + i.toString(),
                requesFile
            )
        }


        val call = iRetrofit?.request(
            params = map,
            expert_type = expert_type,
            expert_category = expert_category,
            place = place,
            request_dt_status = request_dt_status
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



    // 보낸요청 상세페이지(N/N)
    fun requset_page(
        request_idx: String,
        completion: (RESPONSE_STATUS, ArrayList<Sendpage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)

        val call = iRetrofit?.sendpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Sendpage> {
            override fun onResponse(call: Call<Sendpage>, response: Response<Sendpage>) {
                var requsetpageArray = ArrayList<Sendpage>()
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            var requsetpageItem = Sendpage(
                                request_idx = it.request_idx,
                                expert_type = it.expert_type,
                                expert_category = it.expert_category,
                                user_nickname = it.user_nickname,
                                user_thumbnail = it.user_thumbnail,
                                datetime = it.datetime,
                                contents = it.contents,
                                thumbnail = it.thumbnail
                            )
                            requsetpageArray.add(requsetpageItem)

                            completion(RESPONSE_STATUS.OKAY, requsetpageArray)
                        }

                        404 -> {
                            completion(RESPONSE_STATUS.NO_CONTENT, requsetpageArray)
                        }
                    }

                }

            }

            override fun onFailure(call: Call<Sendpage>, t: Throwable) {
                Log.d(Constants.TAG, "requestManager_request_page - onFailure() called / t: $t")
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

    // 받은견적 상세페이지(N/N)
    fun receive_page(
        request_idx: String,
        request_log_idx: String,
        completion: (ESTIMATE, ArrayList<Receivepage>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)

        val call = iRetrofit?.receivepage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Receivepage> {
            override fun onResponse(call: Call<Receivepage>, response: Response<Receivepage>) {
                var requsetpageArray = ArrayList<Receivepage>()
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            var requsetpageItem = Receivepage(
                                request_idx = it.request_idx,
                                request_log_idx = it.request_log_idx,
                                expert_name = it.expert_name,
                                expert_image = it.expert_image,
                                expert_type = it.expert_type,
                                expert_category = it.expert_category,
                                expert_price = it.expert_price,
                                expert_place = it.expert_place,
                                category = it.category,
                                price = it.price,
                                user_nickname = it.user_nickname,
                                user_thumbnail = it.user_thumbnail,
                                datetime = it.datetime,
                                request_contents = it.request_contents,
                                response_contents = it.response_contents,
                                thumbnail = it.thumbnail
                            )
                            requsetpageArray.add(requsetpageItem)

                            completion(ESTIMATE.OKAY, requsetpageArray)
                        }

                        404 -> {
                            completion(ESTIMATE.NOT_USER, requsetpageArray)
                        }
                    }

                }

            }

            override fun onFailure(call: Call<Receivepage>, t: Throwable) {
                Log.d(Constants.TAG, "requestManager_request_page - onFailure() called / t: $t")
            }

        })
    }

    // 받은견적 상세페이지(N/N)
    fun receive_payment(
        request_idx: String,
        request_log_idx: String,
        completion: (ESTIMATE) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)

        val call = iRetrofit?.receivepayment(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            completion(ESTIMATE.OKAY)
                        }

                        404 -> {
                            completion(ESTIMATE.NOT_USER)
                        }
                    }

                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "requestManager_request_page - onFailure() called / t: $t")
            }

        })
    }

}
