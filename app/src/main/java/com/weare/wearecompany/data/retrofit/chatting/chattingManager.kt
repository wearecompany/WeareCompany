package com.weare.wearecompany.data.retrofit.chatting

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.chatting.ChatDatail
import com.weare.wearecompany.data.chatting.ChatList
import com.weare.wearecompany.data.chatting.ChatLog
import com.weare.wearecompany.data.chatting.Detail
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class chattingManager {

    companion object {
        val instance = chattingManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun detail(
        reserve_idx: String,
        request_idx: String,
        request_log_idx: String,
        app_type: Int,
        completion: (RESPONSE_STATUS, ArrayList<Detail>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("reserve_idx", reserve_idx)
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)
        `object`.addProperty("app_type", app_type)

        val call = iRetrofit?.chatdetail(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Detail> {
            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {

                response.body()?.let {

                    when (response.code()) {
                        200 -> {
                            var detailArry = ArrayList<Detail>()

                            val detailItem = Detail(
                                chat_idx = it.chat_idx,
                                type = it.type,
                                user_idx = it.user_idx,
                                expert_user_idx = it.expert_user_idx,
                                opponent_nickname = it.opponent_nickname,
                                reserve_idx = it.reserve_idx,
                                request_idx = it.request_idx,
                                request_log_idx = it.request_log_idx,
                                expert_type = it.expert_type,
                                status = it.status,
                                report_status = it.report_status,
                                refund_status = it.refund_status,
                                chat_end = it.chat_end
                            )
                            detailArry.add(detailItem)
                            completion(RESPONSE_STATUS.OKAY, detailArry)
                        }
                        404 -> {

                        }
                    }
                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - detail - onFailure() called / t: $t")
            }
        })
    }

    fun servicedetail(
        user_idx: Int,
        expert_type: String,
        expert_idx: String,
        expert_user_idx: String,
        completion: (RESPONSE_STATUS, ArrayList<ChatDatail>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("expert_type", expert_type)
        `object`.addProperty("expert_idx", expert_idx)
        `object`.addProperty("expert_user_idx", expert_user_idx)

        val call = iRetrofit?.chatservicedetail(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ChatDatail> {
            override fun onResponse(call: Call<ChatDatail>, response: Response<ChatDatail>) {

                response.body()?.let {

                    when (response.code()) {
                        201 -> {
                            var detailArry = ArrayList<ChatDatail>()

                            val detailItem = ChatDatail(
                                chat_idx = it.chat_idx,
                                user_idx = it.user_idx,
                                expert_user_idx = it.expert_user_idx,
                                oppenent_nickname = it.oppenent_nickname,
                                expert_type = it.expert_type,
                                expert_idx = it.expert_idx,
                                room_id = it.room_id,
                                report_status = it.report_status,
                            )
                            detailArry.add(detailItem)
                            completion(RESPONSE_STATUS.OKAY, detailArry)
                        }
                        404 -> {

                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChatDatail>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - detail - onFailure() called / t: $t")
            }
        })
    }

    fun img(
        chat_idx: String,
        chat_image: File,
        completion: (RESPONSE_STATUS, origin_url: String, resize_url: String) -> Unit
    ) {

        val idx = RequestBody.create("text/plain".toMediaTypeOrNull(), chat_idx)
        val file = RequestBody.create("image/png".toMediaTypeOrNull(), chat_image)

        val Requestfile: MultipartBody.Part = MultipartBody.Part.createFormData(
            "chat_image",
            "chat_image",
            file
        )

        val call = iRetrofit?.chatimg(chat_idx = idx, chat_image = Requestfile).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                response.body()?.let {
                    when (response.code()) {
                        201 -> {
                            completion(
                                RESPONSE_STATUS.OKAY,
                                response.body()!!.get("origin_url").asString,
                                response.body()!!.get("resize_url").asString
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - img - onFailure() called / t: $t")
            }

        })

    }


    fun individual(
        user_idx: Int,
        expert_type: String,
        expert_idx: String,
        expert_user_idx: String,
        completion: (RESPONSE_STATUS, ArrayList<ChatDatail>) -> Unit
    ) {

        iRetrofit?.detailchat(user_idx,expert_type,expert_idx,expert_user_idx)?.enqueue(object : retrofit2.Callback<ChatDatail> {
            override fun onResponse(call: Call<ChatDatail>, response: Response<ChatDatail>) {
                val individualArray = ArrayList<ChatDatail>()
                response.body()?.let {
                    when(response.code()) {
                        200 -> {
                            val individualItem = ChatDatail(
                                chat_idx = it.chat_idx,
                                user_idx = it.user_idx,
                                expert_user_idx = it.expert_user_idx,
                                oppenent_nickname = it.oppenent_nickname,
                                expert_type = it.expert_type,
                                expert_idx = it.expert_idx,
                                room_id = it.room_id,
                                report_status = it.report_status
                            )
                            individualArray.add(individualItem)
                            completion(RESPONSE_STATUS.OKAY,individualArray)
                        }
                        502 -> {

                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChatDatail>, t: Throwable) {

            }

        })

    }

    fun list(user_idx: String, completion: (RESPONSE_STATUS, ArrayList<ChatList>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.chatlist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ChatList> {
            override fun onResponse(call: Call<ChatList>, response: Response<ChatList>) {
                var listArry = ArrayList<ChatList>()
                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            listArry.add(it)
                            completion(RESPONSE_STATUS.OKAY,listArry)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChatList>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - log - onFailure() called / t: $t")
            }

        })
    }

    fun out(chat_idx: String, completion: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("chat_idx", chat_idx)

        val call = iRetrofit?.chatout(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    when (response.code()) {
                        201 -> {
                            completion(RESPONSE_STATUS.OKAY)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - log - onFailure() called / t: $t")
            }

        })
    }

    fun report_reserve(chat_idx: String, claim_idx: String, defandant_idx: String, report_type: Int, report_contents: String, completion: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("chat_idx", chat_idx)
        `object`.addProperty("claim_idx", claim_idx)
        `object`.addProperty("defandant_idx", defandant_idx)
        `object`.addProperty("report_type", report_type)
        `object`.addProperty("report_contents", report_contents)

        val call = iRetrofit?.chatreportreserve(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    when (response.code()) {
                        201 -> {
                            completion(RESPONSE_STATUS.OKAY)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - report_reserve - onFailure() called / t: $t")
            }

        })
    }

    fun report_request(chat_idx: String, claim_idx: String, defandant_idx: String, report_type: Int, report_contents: String, completion: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("chat_idx", chat_idx)
        `object`.addProperty("claim_idx", claim_idx)
        `object`.addProperty("defandant_idx", defandant_idx)
        `object`.addProperty("report_type", report_type)
        `object`.addProperty("report_contents", report_contents)

        val call = iRetrofit?.chatreportrequest(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    when (response.code()) {
                        201 -> {
                            completion(RESPONSE_STATUS.OKAY)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "chattingManager - report_reserve - onFailure() called / t: $t")
            }

        })
    }
}