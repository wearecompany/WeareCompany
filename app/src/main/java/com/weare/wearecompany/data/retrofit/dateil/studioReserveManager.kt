package com.weare.wearecompany.data.retrofit.dateil

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Response

class studioReserveManager {

    companion object {
        val instance = studioReserveManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun reserve(
        user_idx: String,
        expert_user_idx: String,
        expert_idx: String,
        room_idx: String,
        reserve_dt: List<String>,
        reserve_contents: String,
        completion: (RESPONSE_STATUS) -> Unit
    ) {

        val `object` = JsonObject()
        val test = JsonArray()
        for (i in reserve_dt) {
            test.add(i)
        }

        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("expert_user_idx", expert_user_idx)
        `object`.addProperty("expert_idx", expert_idx)
        `object`.addProperty("room_idx", room_idx)
        `object`.add("reserve_dt",test)
        `object`.addProperty("reserve_contents", reserve_contents)

        val call = iRetrofit?.reservestudio(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                response.body()?.let {
                    when(response.code()) {
                        201 -> {
                            completion(RESPONSE_STATUS.OKAY)
                        }
                        404 -> {

                        }
                        502 -> {

                        }
                    }
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "expertReserveManager - reserve - onFailure() called / t: $t")
            }

        })
    }




}