package com.weare.wearecompany.data.retrofit.List.studio

import android.util.Log
import com.weare.wearecompany.data.model.list.data.Model
import com.weare.wearecompany.data.model.list.data.Dmodel
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

class modelListManager {

    companion object {
        val instant = modelListManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun listdata(
            array: JsonArray,
            location: JsonArray,
            datatime: JsonArray,
            lowmoney: String,
            maxmoney: String,
            sort: Int,
            page: Int, completion: (RESPONSE_STATUS, ArrayList<newlist>, ArrayList<Dmodel>) -> Unit) {

        val `object` = JsonObject()
        `object`.add("sub_category", array)
        `object`.add("location", location)
        `object`.add("datatime", datatime)
        if (lowmoney != "") {
            `object`.addProperty("lowmoney", lowmoney.toInt())
        } else {
            `object`.addProperty("lowmoney", lowmoney)
        }
        if (maxmoney != "") {
            `object`.addProperty("maxmoney", maxmoney.toInt())
        } else {
            `object`.addProperty("maxmoney", maxmoney)
        }
        `object`.addProperty("sort", sort)
        `object`.addProperty("page", page)

        val call = iRetrofit?.model(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Model> {

            // 응답 실패시
            override fun onFailure(call: Call<Model>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

                //completion(RESPONSE_STATUS.FAIL,null,null,null,null,null,null)
            }

            override fun onResponse(call: Call<Model>, response: Response<Model>) {

                response.body()?.let {

                    var modelArray = ArrayList<Dmodel>()
                    var newArray = ArrayList<newlist>()


                    it.new.forEach {
                        val newItem = newlist(
                            idx = it.idx,
                            name = it.name,
                            thumbnail = it.thumbnail
                        )
                        newArray.add(newItem)
                    }

                    it.model.forEach() {
                        val photographerItem = Dmodel(
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
                        modelArray.add(photographerItem)
                    }

                    completion(RESPONSE_STATUS.OKAY, newArray,modelArray)
                }

            }
        })
    }
}