package com.weare.wearecompany.data.retrofit.dateil

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.weare.wearecompany.data.photo.dateil.list.date.dateilRent
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class rentDateilManager {

    companion object {
        val instance = rentDateilManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //메인 데이터 호출
    fun date(user_idx: String?, expert_idx: String?, completion: (RESPONSE_STATUS, ArrayList<dateilRent>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("expert_idx", expert_idx)

        val call = iRetrofit?.dateilrent(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<dateilRent> {

            // 응답 실패시
            override fun onFailure(call: Call<dateilRent>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<dateilRent>, response: Response<dateilRent>) {

                response.body()?.let {

                    var dateilArray = ArrayList<dateilRent>()

                    val dateilItem = dateilRent(
                        idx = response.body()!!.idx,
                        like_status = response.body()!!.like_status,
                        user_nickname = response.body()!!.user_nickname,
                        images = response.body()!!.images,
                        sub_category = response.body()!!.sub_category,
                        shop_nickname = response.body()!!.shop_nickname,
                        title = response.body()!!.title,
                        latitude = response.body()!!.latitude,
                        longitude = response.body()!!.longitude,
                        address = response.body()!!.address,
                        expert_user_idx = response.body()!!.expert_user_idx,
                        thumbnail = response.body()!!.thumbnail,
                        info = response.body()!!.info,
                        rule = response.body()!!.rule,
                        grade_avr = response.body()!!.grade_avr,
                        review = response.body()!!.review,
                        like = response.body()!!.like,
                        scrap = response.body()!!.scrap,
                        review_num = response.body()!!.review_num
                    )
                    dateilArray.add(dateilItem)
                    completion(RESPONSE_STATUS.OKAY, dateilArray)
                }
            }
        })
    }

    fun reserve(user_idx: String, expert_user_idx: String, expert_idx: String,dt_status: Boolean,reserve_dt: List<String>,reserve_day: Int,reserve_contents: String, completion: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        val test = JsonArray()
        for (i in reserve_dt) {

            test.add(i)
        }
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("expert_user_idx", expert_user_idx)
        `object`.addProperty("expert_idx", expert_idx)
        `object`.addProperty("dt_status", dt_status)
        `object`.add("reserve_dt", test)
        `object`.addProperty("reserve_day", reserve_day)
        `object`.addProperty("reserve_contents", reserve_contents)





        val call = iRetrofit?.reserverent(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {

            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
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
        })
    }


}

internal fun JsonObject.addProperty(s: String, reserveDt: List<String>) {

}
