package com.weare.wearecompany.data.retrofit.List.studio

import android.util.Log
import com.weare.wearecompany.data.List.model.photographer.Photo
import com.weare.wearecompany.data.List.model.photographer.data.photo
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

class photographerListManager {

    companion object {
        val instant = photographerListManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun listdata(
        array: JsonArray,
        location: JsonArray,
        datatime: JsonArray,
        lowmoney: String,
        maxmoney: String,
        nickname: String,
        sort: Int,
        page: Int
        , completion:(RESPONSE_STATUS, ArrayList<newlist>,ArrayList<photo>) -> Unit) {

        val `object` = JsonObject()
        `object`.add("sub_category",array)
        `object`.add("location",location)
        `object`.add("datatime",datatime)
        if (lowmoney != ""){
            `object`.addProperty("lowmoney",lowmoney.toInt())
        } else {
            `object`.addProperty("lowmoney",lowmoney)
        }
        if (maxmoney != "") {
            `object`.addProperty("maxmoney",maxmoney.toInt())
        } else {
            `object`.addProperty("maxmoney",maxmoney)
        }
        `object`.addProperty("nickname",nickname)
        `object`.addProperty("sort",sort)
        `object`.addProperty("page",page)

        val call = iRetrofit?.photographer(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Photo> {

            // 응답 실패시
            override fun onFailure(call: Call<Photo>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

                //completion(RESPONSE_STATUS.FAIL,null,null,null,null,null,null)
            }

            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {

                response.body()?.let {

                    var photographerArray = ArrayList<photo>()
                    var newArray = ArrayList<newlist>()

                    it.new.forEach {
                        val newItem = newlist(
                            idx = it.idx,
                            name = it.name,
                            thumbnail = it.thumbnail
                        )
                        newArray.add(newItem)
                    }

                    it.photo.forEach(){

                        val photographerItem = photo(
                            idx = it.idx,
                            profile = it.profile,
                            image = it.image,
                            location = it.location,
                            price = it.price,
                            sub = it.sub,
                            title = it.title,
                            sub_category = it.sub_category,
                            grade = it.grade,
                        )
                        photographerArray.add(photographerItem)
                    }
                    completion(RESPONSE_STATUS.OKAY,newArray,photographerArray)
                }

            }
        })
    }

}