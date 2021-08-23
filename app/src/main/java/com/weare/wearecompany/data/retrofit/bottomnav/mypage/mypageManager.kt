package com.weare.wearecompany.data.retrofit.bottomnav.mypage

import android.util.Log
import com.google.firebase.installations.remote.TokenResult
import com.weare.wearecompany.data.bottomnav.mypage.data.manual
import com.weare.wearecompany.data.bottomnav.mypage.data.notice
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.mypage.*
import com.weare.wearecompany.data.bottomnav.mypage.data.myreview
import com.weare.wearecompany.utils.LIKE
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class mypageManager {

    companion object {
        val instance = mypageManager()
    }

    val `object` = JsonObject()

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //메인 데이터 호출
    fun data(idx: String?, completion: (RESPONSE_STATUS, ArrayList<Mypage>) -> Unit) {

        `object`.addProperty("idx", idx)

        val call = iRetrofit?.mypage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Mypage> {

            // 응답 실패시
            override fun onFailure(call: Call<Mypage>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<Mypage>, response: Response<Mypage>) {

                response.body()?.let {

                    var mypageArray = ArrayList<Mypage>()

                    val mypageItem = Mypage(
                        profile_image = response.body()!!.profile_image,
                        nickname = response.body()!!.nickname,
                        like_num = response.body()!!.like_num,
                        review_num = response.body()!!.review_num
                    )
                    mypageArray.add(mypageItem)
                    completion(RESPONSE_STATUS.OKAY, mypageArray)
                }
            }
        })
    }

    fun logout(idx: String, completion: (RESPONSE_STATUS) -> Unit) {
        `object`.addProperty("idx", idx)
        val call = iRetrofit?.logout(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when (response.code()) {
                    200 -> {
                        completion(RESPONSE_STATUS.OKAY)
                    }
                    202 -> {
                        completion(RESPONSE_STATUS.LOGOUT_USER)
                    }
                    404 -> {
                        completion(RESPONSE_STATUS.NOT_USER)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - logout() called / t: $t")
            }

        })
    }

    fun information(completion: (RESPONSE_STATUS, ArrayList<manual>) -> Unit) {

        val call = iRetrofit?.information().let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Information> {

            override fun onFailure(call: Call<Information>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            override fun onResponse(call: Call<Information>, response: Response<Information>) {

                response.body()?.let {

                    var informationArray = ArrayList<manual>()

                    it.manual.forEach { result ->

                        val Item = manual(
                            type = result.type,
                            title = result.title,
                            question = result.question,
                            contents = result.contents
                        )
                        informationArray.add(Item)
                    }
                    completion(RESPONSE_STATUS.OKAY, informationArray)
                }
            }
        })
    }

    fun notice(completion: (RESPONSE_STATUS, ArrayList<notice>) -> Unit) {

        val call = iRetrofit?.notice().let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Notice> {

            override fun onFailure(call: Call<Notice>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            override fun onResponse(call: Call<Notice>, response: Response<Notice>) {

                response.body()?.let {

                    var noticeArray = ArrayList<notice>()

                    it.notice.forEach { result ->
                        val Item = notice(
                            title = result.title,
                            datetime = result.datetime,
                            text_contents = result.text_contents
                        )
                        noticeArray.add(Item)
                    }
                    completion(RESPONSE_STATUS.OKAY, noticeArray)
                }
            }
        })
    }

    fun myreviewlist(user_idx:String, completion: (RESPONSE_STATUS, ArrayList<myreview>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.myreviewlist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<MyReviewList> {
            override fun onResponse(call: Call<MyReviewList>, response: Response<MyReviewList>) {
                val reviewArray = ArrayList<myreview>()
                when(response.code()) {
                    200 -> {

                        response.body()?.let {

                            response.body()!!.review.forEach {
                                val reviewItem = myreview(
                                    review_idx = it.review_idx,
                                    user_nickname = it.user_nickname,
                                    user_thumbnail = it.user_thumbnail,
                                    grade = it.grade,
                                    review_dt = it.review_dt,
                                    expert_type = it.expert_type,
                                    expert_nickname = it.expert_nickname,
                                    expert_thumbnail = it.expert_thumbnail,
                                    expert_category = it.expert_category,
                                    review_contents = it.review_contents,
                                    review_image = it.review_image,
                                    review_reply = it.review_reply,
                                )
                                reviewArray.add(reviewItem)
                            }
                            completion(RESPONSE_STATUS.OKAY, reviewArray)
                        }
                    }
                    404 -> {
                        completion(RESPONSE_STATUS.NO_CONTENT, reviewArray)
                    }
                    502 -> {
                        completion(RESPONSE_STATUS.NO_JOIN, reviewArray)
                    }
                }
            }

            override fun onFailure(call: Call<MyReviewList>, t: Throwable) {
                Log.d(Constants.TAG, "MemberManager_mypage - onFailure() called / t: $t")
            }
        })
    }

    fun like_on(target_type:Int, target_idx:String, user_idx:String, completion: (LIKE) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("target_type", target_type)
        `object`.addProperty("target_idx", target_idx)
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.likeOn(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when(response.code()) {
                    201 -> {
                        completion(LIKE.OKAY)
                    }
                    404 -> {
                        completion(LIKE.NOT_USER_AND_EXPERT)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "mypageManager - like_on() called / t: $t")
            }

        })
    }

    fun like_off(target_type:Int, target_idx:String, user_idx:String, completion: (LIKE) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("target_type", target_type)
        `object`.addProperty("target_idx", target_idx)
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.likeOff(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when(response.code()) {
                    201 -> {
                        completion(LIKE.OKAY)
                    }
                    404 -> {
                        completion(LIKE.NOT_LIKE)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "mypageManager - like_on() called / t: $t")
            }

        })
    }

    fun like_list(idx:String, completion: (LIKE, ArrayList<Bookmark>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", idx)

        val call = iRetrofit?.likelist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Bookmark> {
            override fun onResponse(call: Call<Bookmark>, response: Response<Bookmark>) {
                val bookmarklist = ArrayList<Bookmark>()
                response.body()?.let {

                    when(response.code()) {
                        200 -> {
                            val bookmarkItem = Bookmark(
                                Studio = it.Studio,
                                Photo = it.Photo,
                                Model = it.Model,
                                Trip = it.Trip,
                                Rent = it.Rent
                            )
                            bookmarklist.add(bookmarkItem)
                            completion(LIKE.OKAY,bookmarklist)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Bookmark>, t: Throwable) {
                Log.d(Constants.TAG, "mypageManager_like_list - onFailure() called / t: $t")
            }

        })
    }
}