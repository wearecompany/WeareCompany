package com.weare.wearecompany.data.retrofit.bottomnav.main

import android.util.Log
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.progress.Review
import com.weare.wearecompany.data.division.Division
import com.weare.wearecompany.data.division.DivisionPage
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.division.data.recent
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.ESTIMATE
import retrofit2.Call
import retrofit2.Response

class DivisionManager {

    companion object {
        val instance = DivisionManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun list(completion: (ESTIMATE, ArrayList<recent>,ArrayList<division>) -> Unit) {

        iRetrofit?.divisionlist()?.enqueue(object : retrofit2.Callback<Division> {
            override fun onResponse(
                call: Call<Division>,
                response: Response<Division>
            ) {
                val recentArray = ArrayList<recent>()
                val divisionArray = ArrayList<division>()

                response.body()?.let { it ->
                    it.recent.forEach{
                       val recentItem = recent(
                           model_idx = it.model_idx,
                           generation = it.generation,
                           name = it.name,
                           img = it.img
                       )
                        recentArray.add(recentItem)
                    }
                    it.division.forEach {
                        val divisionItem = division(
                            model_idx = it.model_idx,
                            name = it.name,
                            img = it.img,
                            category = it.category,
                            title = it.title,
                            view_num = it.view_num,
                            like_num = it.like_num,
                            grade = it.grade,
                            generation = it.generation,
                        )
                        divisionArray.add(divisionItem)
                    }
                    completion(ESTIMATE.OKAY,recentArray,divisionArray)
                }
            }

            override fun onFailure(call: Call<Division>, t: Throwable) {
                Log.d(Constants.TAG, "MemberManager_edit_stdio - onFailure() called / t: $t")
            }

        })
    }

    fun page( user_idx: String,
              model_idx: String, completion: (ESTIMATE, ArrayList<DivisionPage>) -> Unit) {

        iRetrofit?.divisionpage(user_idx,model_idx)?.enqueue(object : retrofit2.Callback<DivisionPage> {
            override fun onResponse(
                call: Call<DivisionPage>,
                response: Response<DivisionPage>
            ) {
                val divisionArray = ArrayList<DivisionPage>()

                response.body()?.let { it ->
                        val divisionItem = DivisionPage(
                            model_idx = it.model_idx,
                            thumbnail = it.thumbnail,
                            model_img = it.model_img,
                            model_title=it.model_title,
                            view_num = it.view_num,
                            like_num = it.like_num,
                            model_name = it.model_name,
                            model_category = it.model_category,
                            video_thumbnail = it.video_thumbnail,
                            video_link = it.video_link,
                            hashtag = it.hashtag,
                            title = it.title,
                            scrap_status = it.scrap_status,
                            qa  = it.qa
                        )
                    divisionArray.add(divisionItem)
                    completion(ESTIMATE.OKAY,divisionArray)
                }
            }

            override fun onFailure(call: Call<DivisionPage>, t: Throwable) {
                Log.d(Constants.TAG, "MemberManager_edit_stdio - onFailure() called / t: $t")
            }

        })
    }
}