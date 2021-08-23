package com.weare.wearecompany.data.retrofit.bottomnav.mypage

import android.util.Log
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class editUserManager {

    companion object{
        val instanc = editUserManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //메인 데이터 호출
    fun data(mode: String?, user_idx: String?, verify_String : String?, completion: (RESPONSE_STATUS, Int) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("mode", mode)
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("verify_string", verify_String)

        val call = iRetrofit?.edituser(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {

            // 응답 실패시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

            // 응답 실패시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                response.body()?.let {

                    var code =response.code()

                    completion(RESPONSE_STATUS.OKAY, code)
                }
            }
        })
    }


    fun profileupload(idx: String?, nickname:String?, image:File?, completion: (RESPONSE_STATUS, Int) -> Unit) {


        val detalist: MultipartBody.Part

        val requesFile = image?.let { RequestBody.create("image/png".toMediaTypeOrNull(), it) }
        val nickName = nickname?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val Idx = idx?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }


        //val requesFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)

        detalist = requesFile?.let { MultipartBody.Part.createFormData("image",image?.name, it) }!!

        val map:HashMap<String, RequestBody> = HashMap()
        Idx?.let { map.put("idx", it) }
        nickName?.let { map.put("nickname", it) }
        map.put("image",requesFile)


        val call = iRetrofit?.editProfile(idx = idx, nickname = nickname, image = detalist).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {

                    var code =response.code()

                    completion(RESPONSE_STATUS.OKAY, code)
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

        })

    }

    fun profiletest(idx: String?, nickname:String?, image:File?, completion: (RESPONSE_STATUS, Int) -> Unit) {


        val detalist: MultipartBody.Part

        val requesFile = image?.let { RequestBody.create("image/png".toMediaTypeOrNull(), it) }
        val nickName = nickname?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val Idx = idx?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }


        //val requesFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)

        detalist = requesFile?.let { MultipartBody.Part.createFormData("image",image?.name, it) }!!
        val map:HashMap<String, RequestBody> = HashMap()
        Idx?.let { map.put("idx", it) }
        nickName?.let { map.put("nickname", it) }
        map.put("image\"; filename=\"" +"testimage", requesFile )


        val call = iRetrofit?.edittest(params = map).let {
            it
        } ?: return


        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {

                    var code =response.code()

                    completion(RESPONSE_STATUS.OKAY, code)
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "RetrofitManager - onFailure() called / t: $t")
            }

        })

    }
}