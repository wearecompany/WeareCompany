package com.weare.wearecompany.data.retrofit.List.studio

import android.util.Log
import com.weare.wearecompany.data.List.trip.Trip
import com.weare.wearecompany.data.List.trip.data.trip
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.weare.wearecompany.data.model.list.data.newlist
import retrofit2.Call
import retrofit2.Response

class tripListManager {

    companion object {
        val instant = tripListManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun listdata( array: JsonArray, location: JsonArray, min: String,
                  max: String,nickname:String,sort: Int, page: Int, completion:(RESPONSE_STATUS, ArrayList<newlist>, ArrayList<trip>) -> Unit) {

        val `object` = JsonObject()
        `object`.add("sub_category", array)
        `object`.add("location", location)
        if (min != "") {
            `object`.addProperty("lowmoney", min.toInt())
        } else {
            `object`.addProperty("lowmoney", min)
        }
        if (max != "") {
            `object`.addProperty("maxmoney", max.toInt())
        } else {
            `object`.addProperty("maxmoney", max)
        }
        `object`.addProperty("nickname", nickname)
        `object`.addProperty("sort", sort)
        `object`.addProperty("page", page)

        val call = iRetrofit?.trip(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Trip> {

            // 응답 실패시
            override fun onFailure(call: Call<Trip>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

            }

            override fun onResponse(call: Call<Trip>, response: Response<Trip>) {

                response.body()?.let {

                    var tripArray = ArrayList<trip>()
                    var newArray = ArrayList<newlist>()


                    it.new.forEach {
                        val newItem = newlist(
                            idx = it.idx,
                            name = it.name,
                            thumbnail = it.thumbnail
                        )
                        newArray.add(newItem)
                    }

                    it.rent.forEach {

                        val tripItem = trip(
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
                        tripArray.add(tripItem)
                    }
                    completion(RESPONSE_STATUS.OKAY,newArray,tripArray)
                }
            }
        })
    }
}