package com.weare.wearecompany.data.retrofit

import android.util.Log
import com.google.gson.JsonObject
import com.weare.wearecompany.data.join.Join
import com.weare.wearecompany.data.login.Login
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Response

class MemberManager {

    companion object {
        val instance = MemberManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun login(login_type:Int, user_email:String, uid:String, completion: (RESPONSE_STATUS, ArrayList<Login>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("login_type", login_type)
        `object`.addProperty("user_email", user_email)
        `object`.addProperty("uid", uid)

        val call = iRetrofit?.login(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                var loginArray = ArrayList<Login>()
                if (response.code() == 200) {
                    response.body()?.let {
                        val loginItem = Login(
                            user_idx = it.user_idx,
                            user_nickname = it.user_nickname,
                            user_image = it.user_image,
                            user_type = it.user_type,
                            user_push = it.user_push,
                            user_marketing = it.user_marketing,
                            user_token = it.user_token
                        )
                        loginArray.add(loginItem)
                        completion(RESPONSE_STATUS.OKAY, loginArray)
                    }
                } else if (response.code() == 500) {
                    completion(RESPONSE_STATUS.NO_CONTENT, loginArray)
                } else if (response.code() == 404) {
                    completion(RESPONSE_STATUS.NOT_USER, loginArray)
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.d(Constants.TAG, "MemberManager_login - onFailure() called / t: $t")
            }

        })
    }

    fun join(user_uid: String?, user_login_type:Int, user_email: String?, user_profile_image:String?, user_nickname:String, user_marketing_agree:Boolean, user_recommend:String, user_refresh_token:String?,  completion: (RESPONSE_STATUS, ArrayList<Join>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_uid", user_uid)
        `object`.addProperty("user_login_type", user_login_type)
        `object`.addProperty("user_email", user_email)
        `object`.addProperty("user_profile_image", user_profile_image)
        `object`.addProperty("user_nickname", user_nickname)
        `object`.addProperty("user_marketing_agree", user_marketing_agree)
        `object`.addProperty("user_recommend", user_recommend)
        `object`.addProperty("user_refresh_token", user_refresh_token)

        val call = iRetrofit?.join(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Join> {
            override fun onResponse(call: Call<Join>, response: Response<Join>) {
                var joinArray = ArrayList<Join>()
                if (response.code() == 201) {
                    response.body()?.let {
                        val joinItem = Join(
                            user_idx = it.user_idx,
                            user_nickname = it.user_nickname,
                            user_image = it.user_image,
                            user_type = it.user_type,
                            user_push = it.user_push,
                            user_marketing = it.user_marketing,
                            user_token = it.user_token
                        )
                        joinArray.add(joinItem)
                        completion(RESPONSE_STATUS.OKAY, joinArray)
                    }
                } else if (response.code() == 204) {
                    completion(RESPONSE_STATUS.NO_JOIN, joinArray)
                }
            }

            override fun onFailure(call: Call<Join>, t: Throwable) {
                Log.d(Constants.TAG, "MemberManager_join - onFailure() called / t: $t")
            }

        })


    }
}