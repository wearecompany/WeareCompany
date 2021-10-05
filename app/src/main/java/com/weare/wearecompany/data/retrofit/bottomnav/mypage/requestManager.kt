package com.weare.wearecompany.data.retrofit.bottomnav.mypage

import android.util.Log
import com.google.gson.JsonObject
import com.weare.wearecompany.data.main.Request.TopList
import com.weare.wearecompany.data.main.Request.deta.top
import com.weare.wearecompany.data.main.Request.requestReceiveList
import com.weare.wearecompany.data.main.Request.requestReviewList
import com.weare.wearecompany.data.main.Request.requestSendList
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Response

class requestManager {

    companion object {
        val instance = requestManager()
    }

    val `object` = JsonObject()

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //결제된 top 리스트
    fun topList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<top>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requesttopList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<TopList> {

            // 응답 실패시
            override fun onFailure(call: Call<TopList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<TopList>, response: Response<TopList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var topArray = ArrayList<top>()

                            response.body()?.top?.forEach {
                                val mypageItem = top(
                                    reserve_idx = it.reserve_idx,
                                    reserve_type = it.reserve_type,
                                    reserve_status = it.reserve_status,
                                    expert_name = it.expert_name,
                                    expert_type = it.expert_type,
                                    send_time = it.send_time,
                                    room_name = it.room_name,
                                    head_count = it.head_count,
                                    price = it.price,
                                    time = it.time,
                                    date = it.date,
                                )
                                topArray.add(mypageItem)
                            }
                            completion(RESPONSE_STATUS.OKAY, topArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //보낸요청 리스트
    fun sendList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestSendList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestSendList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestSendList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestSendList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestSendList>, response: Response<requestSendList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestSendList>()

                            val mypageItem = requestSendList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //받은견적 리스트
    fun receiveList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestReceiveList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestReceiveList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestReceiveList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestReceiveList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestReceiveList>, response: Response<requestReceiveList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestReceiveList>()

                            val mypageItem = requestReceiveList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //결제완료 리스트
    fun progressList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestReceiveList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestProgressList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestReceiveList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestReceiveList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestReceiveList>, response: Response<requestReceiveList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestReceiveList>()

                            val mypageItem = requestReceiveList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //진행완료 리스트
    fun progressOkList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestReceiveList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestProgressOkList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestReceiveList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestReceiveList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestReceiveList>, response: Response<requestReceiveList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestReceiveList>()

                            val mypageItem = requestReceiveList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //후기작성 리스트
    fun reviewList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestReviewList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestReviewList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestReviewList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestReviewList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestReviewList>, response: Response<requestReviewList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestReviewList>()

                            val mypageItem = requestReviewList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //취소요청 리스트
    fun refundList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestReceiveList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestRefundList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestReceiveList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestReceiveList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestReceiveList>, response: Response<requestReceiveList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestReceiveList>()

                            val mypageItem = requestReceiveList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }

    //취소완료 리스트
    fun refundOkList(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<requestReceiveList>) -> Unit) {

        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.requestRefundOkList(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<requestReceiveList> {

            // 응답 실패시
            override fun onFailure(call: Call<requestReceiveList>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<requestReceiveList>, response: Response<requestReceiveList>) {

                when(response.code()) {
                    200 -> {
                        response.body()?.let {

                            var sendArray = ArrayList<requestReceiveList>()

                            val mypageItem = requestReceiveList(
                                reserve = it.reserve,
                                oneClick = it.oneClick,
                                request = it.request,
                                response = it.response,
                                progress = it.progress,
                                complete = it.complete,
                                review = it.review,
                                refund_request = it.refund_request,
                                refund_complete = it.refund_complete
                            )
                            sendArray.add(mypageItem)
                            completion(RESPONSE_STATUS.OKAY, sendArray)
                        }
                    }
                    404 -> {

                    }
                }

            }
        })
    }


}