package com.weare.wearecompany.data.retrofit.hotpick

import android.util.Log
import com.weare.wearecompany.data.hotpick.datail
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response


class HotPickDataManager {

    companion object {
        val instance = HotPickDataManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //메인 데이터 호출
    fun data(
        user_idx: String,
        idx: String?,
        completion: (RESPONSE_STATUS, ArrayList<datail>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("expert_idx", idx)

        val call = iRetrofit?.hotpickdata(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<datail> {

            // 응답 실패시
            override fun onFailure(call: Call<datail>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

                //completion(RESPONSE_STATUS.FAIL,null,null,null,null,null,null)
            }

            override fun onResponse(call: Call<datail>, response: Response<datail>) {

                response.body()?.let {

                    var datailArray = ArrayList<datail>()

                    val datailItem = datail(
                        idx = response.body()!!.idx,
                        like_status = response.body()!!.like_status,
                        name = response.body()!!.name,
                        images = response.body()!!.images,
                        sub_category = response.body()!!.sub_category,
                        title = response.body()!!.title,
                        latitude = response.body()!!.latitude,
                        longitude = response.body()!!.longitude,
                        address = response.body()!!.address,
                        room = response.body()!!.room,
                        event = response.body()!!.event,
                        expert_user_idx = response.body()!!.expert_user_idx,
                        thumbnail = response.body()!!.thumbnail,
                        nickname = response.body()!!.nickname,
                        info = response.body()!!.info,
                        rule = response.body()!!.rule,
                        parking = response.body()!!.parking,
                        grade_avr = response.body()!!.grade_avr,
                        review = response.body()!!.review,
                        like = response.body()!!.like,
                        scrap = response.body()!!.scrap,
                        review_num = response.body()!!.review_num,
                    )
                    datailArray.add(datailItem)
                    completion(RESPONSE_STATUS.OKAY, datailArray)

                }

            }
        })
    }
}