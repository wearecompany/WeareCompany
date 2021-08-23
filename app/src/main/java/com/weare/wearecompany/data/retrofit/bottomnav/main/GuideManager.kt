package com.weare.wearecompany.data.retrofit.bottomnav.main

import android.util.Log
import com.weare.wearecompany.data.main.Guide
import com.weare.wearecompany.data.main.data.*
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Response

class GuideManager {

    companion object{
        val instance = GuideManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 메인 데이터 호출
    fun deta(
        completion: (RESPONSE_STATUS, ArrayList<guidelist>) -> Unit
    ) {


        val call = iRetrofit?.guidelist().let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Guide> {

            // 응답 실패시
            override fun onFailure(call: Call<Guide>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")

                //completion(RESPONSE_STATUS.FAIL,null,null,null,null,null,null)
            }

            override fun onResponse(call: Call<Guide>, response: Response<Guide>) {

                response.body()?.let {

                    var guidelistArray = ArrayList<guidelist>()

                    it.guideList.forEach { resultItem ->

                        val guideItem = guidelist(
                            idx = resultItem.idx,
                            title = resultItem.title,
                            sub = resultItem.sub,
                            image = resultItem.image
                        )
                        guidelistArray.add(guideItem)
                    }
                    completion(
                        RESPONSE_STATUS.OKAY, guidelistArray)
                }
            }
        })
    }
}