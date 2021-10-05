package com.weare.wearecompany.data.retrofit.List.studio

import android.util.Log
import com.weare.wearecompany.data.main.Studio
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.weare.wearecompany.data.main.data.newStudio
import com.weare.wearecompany.data.model.list.data.newlist
import retrofit2.Call
import retrofit2.Response

class studioListManager {

    companion object {
        val instant = studioListManager()
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
    , completion:(RESPONSE_STATUS, ArrayList<newStudio>, ArrayList<studio>) -> Unit) {

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

        val call = iRetrofit?.studio(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Studio> {

            // 응답 실패시
            override fun onFailure(call: Call<Studio>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

                //completion(RESPONSE_STATUS.FAIL,null,null,null,null,null,null)
            }

            override fun onResponse(call: Call<Studio>, response: Response<Studio>) {

                response.body()?.let {

                    var stdioArray = ArrayList<studio>()
                    var newArray = ArrayList<newStudio>()


                    it.new.forEach {
                        val newItem = newStudio(
                            idx = it.idx,
                            name = it.name,
                            place = it.place,
                            thumbnail = it.thumbnail
                        )
                        newArray.add(newItem)
                    }

                    it.studio.forEach{

                        val studioItem = studio(
                            idx = it.idx,
                            profile = it.profile,
                            image = it.image,
                            sub = it.sub,
                            title = it.title,
                            address = it.address,
                            price = it.price,
                            lat = it.lat,
                            long = it.long,
                            grade = it.grade,
                            event = it.event
                        )
                        stdioArray.add(studioItem)
                    }
                    completion(RESPONSE_STATUS.OKAY,newArray,stdioArray)
                }

            }
        })
    }
}


