package com.weare.wearecompany.data.retrofit.List.studio

import android.util.Log
import com.weare.wearecompany.data.List.rent.Rent
import com.weare.wearecompany.data.List.rent.data.rent
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class rentListManager {

    companion object {
        val instant = rentListManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun listdata(
            array: JsonArray,
            location: JsonArray,
            sort: Int,
            page: Int
            , completion:(RESPONSE_STATUS, ArrayList<rent>) -> Unit) {

        val `object` = JsonObject()
        `object`.add("sub_category",array)
        `object`.add("location",location)
        `object`.addProperty("sort",sort)
        `object`.addProperty("page",page)

        val call = iRetrofit?.rent(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Rent> {

            // 응답 실패시
            override fun onFailure(call: Call<Rent>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

            }

            override fun onResponse(call: Call<Rent>, response: Response<Rent>) {

                response.body()?.let {

                    var rentArray = ArrayList<rent>()

                    it.rent.forEach {

                        val rentItem = rent(
                                idx = it.idx,
                                profile = it.profile,
                                image = it.image,
                                location = it.location,
                                price = it.price,
                                sub = it.sub,
                                title = it.title,
                                sub_category = it.sub_category,
                                grade = it.grade,
                                event = it.event
                        )
                        rentArray.add(rentItem)
                    }
                    completion(RESPONSE_STATUS.OKAY,rentArray)
                }

            }
        })
    }

}