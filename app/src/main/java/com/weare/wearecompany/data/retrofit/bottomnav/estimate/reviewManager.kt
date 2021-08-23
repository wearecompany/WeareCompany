package com.weare.wearecompany.data.retrofit.bottomnav.estimate

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.ESTIMATE
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.io.File

class reviewManager {

    companion object {
        val instance = reviewManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun upload(
        type: Int,
        reserve_idx: String,
        request_idx: String,
        request_log_idx: String,
        grade: Int,
        contents: String,
        review_image: File?,
        completion: (ESTIMATE) -> Unit) {

        var review_image_file: MultipartBody.Part?

        val reserve_idx_body = RequestBody.create("text/plain".toMediaTypeOrNull(), reserve_idx)
        val request_idx_body = RequestBody.create("text/plain".toMediaTypeOrNull(),request_idx)
        val request_log_idx_body = RequestBody.create("text/plain".toMediaTypeOrNull(), request_log_idx)
        val contents_body = RequestBody.create("text/plain".toMediaTypeOrNull(), contents)

        /*val `object` = JsonObject()
        `object`.addProperty("type", type)
        `object`.addProperty("reserve_idx", reserve_idx)
        `object`.addProperty("request_idx", request_idx)
        `object`.addProperty("request_log_idx", request_log_idx)
        `object`.addProperty("grade", grade)
        `object`.addProperty("contents", contents)*/

        if (review_image != null) {
            val review_image_body = RequestBody.create("image/png".toMediaTypeOrNull(), review_image)
            review_image_file =
                MultipartBody.Part.createFormData(
                    "review_image",
                    review_image!!.name,
                    review_image_body
                )
        } else {
            review_image_file = null
        }



        val call = iRetrofit?.reviewupload(type = type,
            reserve_idx = reserve_idx_body,
            request_idx = request_idx_body,
            request_log_idx = request_log_idx_body,
            grade = grade,
            contents = contents_body,
            review_image = review_image_file).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(
                call: Call<JsonElement>,
                response: Response<JsonElement>
            ) {
                response.body()?.let {
                    when(response.code()) {
                        201 -> {
                            completion(ESTIMATE.OKAY)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Timber.d("receiveManager_shopPage - onFailure() called / t: $t")
            }

        })
    }
}