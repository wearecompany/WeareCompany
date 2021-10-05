package com.weare.wearecompany.data.retrofit.bottomnav.main

import android.util.Log
import com.weare.wearecompany.data.main.data.*
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.Constants.TAG
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.mypage.data.allList
import com.weare.wearecompany.data.main.*
import com.weare.wearecompany.data.notification.AlarmList
import com.weare.wearecompany.data.notification.data.alarm
import com.weare.wearecompany.data.retrofit.CertRetrofitClient
import com.weare.wearecompany.data.studio.main.Main
import retrofit2.Call
import retrofit2.Response

class MainManager {

    companion object {
        val instance = MainManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 레트로핏 인터페이스 가져오기
    private val certRetrofit: IRetrofit? =
        CertRetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 메인 데이터 호출
    fun main(
        user_idx: String?,
        app_version: String?,
        device: String?,
        os: String?,
        os_version: String?,

        completion: (RESPONSE_STATUS, ArrayList<Main>) -> Unit
    ) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("app_version", app_version)
        `object`.addProperty("device", device)
        `object`.addProperty("os", os)
        `object`.addProperty("os_version", os_version)


        val call = iRetrofit?.main(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<Main> {

            // 응답 실패시
            override fun onFailure(call: Call<Main>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")

                //completion(RESPONSE_STATUS.FAIL,null,null,null,null,null,null)
            }

            override fun onResponse(call: Call<Main>, response: Response<Main>) {

                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            var MainArray = ArrayList<Main>()

                            var MainItem = Main(
                                banner = it.banner,
                                list = it.list,
                                bottom_banner = it.bottom_banner,
                                footer = it.footer,
                                company_info = it.company_info,
                                tel = it.tel,
                                sub_category = it.sub_category,
                                sub_max_count = it.sub_max_count,
                                location = it.location,
                                location_max_count = it.location_max_count
                            )
                            MainArray.add(MainItem)
                            completion(RESPONSE_STATUS.OKAY, MainArray)
                        }
                    }


                }
            }
        })
    }

    fun mainlist(expert_type: Int, complation: (RESPONSE_STATUS, ArrayList<list>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("expert_type", expert_type)

        val call = iRetrofit?.mainlist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<HomeList> {
            override fun onResponse(call: Call<HomeList>, response: Response<HomeList>) {

                var homelist = ArrayList<list>()
                    when (response.code()) {
                        200 -> {
                            response.body()?.let {
                                it.home_list.forEach{
                                    val listItem = list(
                                        idx = it.idx,
                                        expert_image = it.expert_image,
                                        expert_name = it.expert_name,
                                        expert_title = it.expert_title,
                                        expert_place = it.expert_place,
                                        expert_category = it.expert_category,
                                    )
                                    homelist.add(listItem)
                                }
                                complation(RESPONSE_STATUS.OKAY, homelist)
                            }

                        }
                        502 -> {

                        }
                    }
            }

            override fun onFailure(call: Call<HomeList>, t: Throwable) {

            }

        })
    }

  /* fun hotpicklist(complation: (RESPONSE_STATUS, ArrayList<hotpick>) -> Unit) {

        val call = iRetrofit?.mainhotpick().let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<HotpickList> {
            override fun onResponse(call: Call<HotpickList>, response: Response<HotpickList>) {

                var hotpicklist = ArrayList<hotpick>()

                when (response.code()) {
                    200 -> {
                        response.body()?.let {

                            it.hotpick.forEach{
                                val Item = hotpick(
                                    idx = it.idx,
                                    target_type = it.target_type,
                                    target_idx = it.target_idx,
                                    name = it.name,
                                    title = it.title,
                                    place = it.place,
                                    category = it.category,
                                    image = it.image,
                                )
                                hotpicklist.add(Item)
                            }

                            complation(RESPONSE_STATUS.OKAY, hotpicklist)
                        }

                    }

                }
            }

            override fun onFailure(call: Call<HotpickList>, t: Throwable) {

            }

        })
    }*/

   /* fun eventlist(complation: (RESPONSE_STATUS, ArrayList<banner>,ArrayList<event>) -> Unit) {

        val call = iRetrofit?.mainevent().let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<EventList> {
            override fun onResponse(call: Call<EventList>, response: Response<EventList>) {

                var bannerlist = ArrayList<banner>()
                var eventlist = ArrayList<event>()

                when (response.code()) {
                    200 -> {
                        response.body()?.let {

                            it.banner.forEach{
                                val Item = banner(
                                    idx = it.idx,
                                    target = it.target,
                                    image = it.image,
                                    url = it.url
                                )
                                bannerlist.add(Item)
                            }

                            it.event.forEach{
                                val Item = event(
                                    idx = it.idx,
                                    title = it.title,
                                    sub = it.sub,
                                    image = it.image,
                                    time = it.time,
                                    goods = it.goods,
                                    person = it.person,
                                    event_type = it.event_type
                                )
                                eventlist.add(Item)
                            }

                            complation(RESPONSE_STATUS.OKAY, bannerlist,eventlist)
                        }

                    }

                }
            }

            override fun onFailure(call: Call<EventList>, t: Throwable) {

            }

        })
    } */

    fun event_page(event_idx: String, complation: (RESPONSE_STATUS, ArrayList<eventpage>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("event_idx", event_idx)

        val call = iRetrofit?.eventpage(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<eventpage> {
            override fun onResponse(call: Call<eventpage>, response: Response<eventpage>) {

                var list = ArrayList<eventpage>()

                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                                val listItem = eventpage(
                                    event_idx = it.event_idx,
                                    event_title = it.event_title,
                                    event_sub_title = it.event_sub_title,
                                    event_datetime = it.event_datetime,
                                    event_contents = it.event_contents,
                                    event_image = it.event_image,
                                    event_type = it.event_type,
                                )
                                list.add(listItem)
                            complation(RESPONSE_STATUS.OKAY, list)
                        }

                    }
                    404 -> {

                    }
                }
            }

            override fun onFailure(call: Call<eventpage>, t: Throwable) {

            }

        })
    }

    fun contentslist(complation: (RESPONSE_STATUS, ArrayList<contents>) -> Unit) {

        val call = iRetrofit?.maincontents().let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<ContentsList> {
            override fun onResponse(call: Call<ContentsList>, response: Response<ContentsList>) {

                var contentslist = ArrayList<contents>()

                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            it.contents.forEach{
                                val Item = contents(
                                    idx = it.idx,
                                    category = it.category,
                                    title = it.title,
                                    sub = it.sub,
                                    image = it.image,
                                    register = it.register,
                                )
                                contentslist.add(Item)
                            }
                            complation(RESPONSE_STATUS.OKAY, contentslist)
                        }
                    }
                    404 -> {

                    }

                }
            }

            override fun onFailure(call: Call<ContentsList>, t: Throwable) {

            }

        })
    }

    fun token(idx: String, toekn: String, completion: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("idx", idx)
        `object`.addProperty("token", toekn)

        val call = iRetrofit?.token(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.code()?.let {
                    if (response.code() == 201) {
                        completion(
                            RESPONSE_STATUS.OKAY
                        )
                    } else if (response.code() == 505) {
                        completion(
                            RESPONSE_STATUS.NO_CONTENT
                        )
                    } else {
                        completion(
                            RESPONSE_STATUS.FAIL
                        )
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "MaonManager_token - onFailure() called / t: $t")
            }

        })
    }

    fun alarmcheck(type: Int, user_idx: String, complation: (RESPONSE_STATUS, Int) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("type", type)
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.notificationcheck(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                response.body()?.let {
                    when (response.code()) {
                        200 -> {
                            val alarm_check = response.body()!!.get("alarm_status").asInt
                            complation(RESPONSE_STATUS.OKAY, alarm_check)
                        }
                        404 -> {
                            complation(RESPONSE_STATUS.OKAY, -1)
                        }
                    }
                }


            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

        })
    }

    fun alarmlist(user_idx: String, complation: (RESPONSE_STATUS, ArrayList<alarm>) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.notificationlist(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<AlarmList> {
            override fun onResponse(call: Call<AlarmList>, response: Response<AlarmList>) {

                var alarmlist = ArrayList<alarm>()

                when (response.code()) {
                     200 -> {
                         response.body()?.let {
                             it.alarm_list.forEach {

                                 val alarmItem = alarm(
                                     idx = it.idx,
                                     type = it.type,
                                     expert_type = it.expert_type,
                                     target_idx = it.target_idx,
                                     title = it.title,
                                     contents = it.contents,
                                     date_diff = it.date_diff,
                                     view_status = it.view_status
                                 )
                                 alarmlist.add(alarmItem)
                             }
                             complation(RESPONSE_STATUS.OKAY, alarmlist)
                         }
                     }
                    404 -> {
                        complation(RESPONSE_STATUS.NO_CONTENT, alarmlist)
                    }
                }


            }

            override fun onFailure(call: Call<AlarmList>, t: Throwable) {
                Log.d(Constants.TAG, "MaonManager_token - onFailure() called / t: $t")
            }

        })
    }

    fun alarmview(alarm_idx: String, complation: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("alarm_idx", alarm_idx)

        val call = iRetrofit?.notificationview(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    201 -> {
                        complation(RESPONSE_STATUS.OKAY)
                    }
                    404 -> {
                        complation(RESPONSE_STATUS.OKAY)
                    }
                }


            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "MaonManager_token - onFailure() called / t: $t")
            }

        })
    }

    fun alarmdelete(alarm_idx: String, complation: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("alarm_idx", alarm_idx)

        val call = iRetrofit?.notificationdelete(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {


                when (response.code()) {
                    201 -> {
                        complation(RESPONSE_STATUS.OKAY)
                    }
                    404 -> {

                    }
                }


            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }

        })
    }

    fun cert(user_idx: String, complation: (RESPONSE_STATUS) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.cert(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()) {
                    200 -> {
                        complation(RESPONSE_STATUS.OKAY)
                    }
                }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constants.TAG, "MaonManager_token - onFailure() called / t: $t")
            }

        })
    }

    fun certcheck(user_idx: String, complation: (RESPONSE_STATUS, Int) -> Unit) {

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)

        val call = iRetrofit?.certcheck(jsonObject = `object`).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                when (response.code()) {
                    200 -> {
                        response.body().let {
                            val check = it!!.get("user_cert").asInt
                            complation(RESPONSE_STATUS.OKAY,check)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d(Constants.TAG, "MaonManager_token - onFailure() called / t: $t")
            }

        })
    }
}