package com.weare.wearecompany.data.retrofit.dateil

import android.util.Log
import com.weare.wearecompany.data.photo.dateil.list.date.dateilPhoto
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class photoDateilManager {

    companion object {
        val instance = photoDateilManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //메인 데이터 호출
    fun date(
        user_idx: String?,
        expert_idx: String?,
        completion: (RESPONSE_STATUS, ArrayList<dateilPhoto>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("expert_idx", expert_idx)

        val call = iRetrofit?.dateilphoto(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<dateilPhoto> {

            // 응답 실패시
            override fun onFailure(call: Call<dateilPhoto>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<dateilPhoto>, response: Response<dateilPhoto>) {

                response.body()?.let {

                    var dateilArray = ArrayList<dateilPhoto>()

                    val dateilItem = dateilPhoto(
                        idx = response.body()!!.idx,
                        like_status = response.body()!!.like_status,
                        name = response.body()!!.name,
                        images = response.body()!!.images,
                        sub_category = response.body()!!.sub_category,
                        title = response.body()!!.title,
                        address = response.body()!!.address,
                        expert_user_idx = response.body()!!.expert_user_idx,
                        thumbnail = response.body()!!.thumbnail,
                        price = response.body()!!.price,
                        career = response.body()!!.career,
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
}