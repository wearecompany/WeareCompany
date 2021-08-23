package com.weare.wearecompany.data.retrofit

import com.weare.wearecompany.data.hotpick.datail
import com.weare.wearecompany.data.join.Join
import com.weare.wearecompany.data.model.list.data.Model
import com.weare.wearecompany.data.List.model.photographer.Photo
import com.weare.wearecompany.data.List.rent.Rent
import com.weare.wearecompany.data.List.trip.Trip
import com.weare.wearecompany.data.bottomnav.estimate.*
import com.weare.wearecompany.data.bottomnav.estimate.send.Sendpage
import com.weare.wearecompany.data.model.dateil.dateilModel
import com.weare.wearecompany.data.photo.dateil.list.date.dateilPhoto
import com.weare.wearecompany.data.photo.dateil.list.date.dateilRent
import com.weare.wearecompany.data.photo.dateil.list.date.dateilTrip
import com.weare.wearecompany.utils.API
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.weare.wearecompany.data.bottomnav.estimate.data.uploadRequest
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressList
import com.weare.wearecompany.data.bottomnav.estimate.progress.Review
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.*
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveList
import com.weare.wearecompany.data.bottomnav.estimate.receive.Receivepage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveExpertPage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveShopPage
import com.weare.wearecompany.data.bottomnav.estimate.receive.data.ReceiveStudioPage
import com.weare.wearecompany.data.bottomnav.estimate.send.SendList
import com.weare.wearecompany.data.bottomnav.estimate.send.data.SendExpertPage
import com.weare.wearecompany.data.bottomnav.estimate.send.data.SendShopPage
import com.weare.wearecompany.data.bottomnav.estimate.send.data.SendStudioPage
import com.weare.wearecompany.data.bottomnav.mypage.*
import com.weare.wearecompany.data.bottomnav.mypage.data.allList
import com.weare.wearecompany.data.chatting.ChatDatail
import com.weare.wearecompany.data.chatting.ChatList
import com.weare.wearecompany.data.chatting.ChatLog
import com.weare.wearecompany.data.chatting.Detail
import com.weare.wearecompany.data.login.Login
import com.weare.wearecompany.data.main.*
import com.weare.wearecompany.data.main.data.eventpage
import com.weare.wearecompany.data.notification.AlarmList
import com.weare.wearecompany.data.studio.main.Main
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {



    @Headers("Content-Type: application/json")
    @POST(API.CERT)
    fun cert(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.CERT_CHECK)
    fun certcheck(@Body jsonObject: JsonObject): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST(API.MAIN)
    fun main(@Body jsonObject: JsonObject): Call<Main>

    @Headers("Content-Type: application/json")
    @POST(API.MAIN_LIST)
    fun mainlist(@Body jsonObject: JsonObject): Call<HomeList>

    @Headers("Content-Type: application/json")
    @POST(API.MAIN_HOTPICK)
    fun mainhotpick(): Call<HotpickList>

    @Headers("Content-Type: application/json")
    @POST(API.MY_REVIEW_LIST)
    fun myreviewlist(@Body jsonObject: JsonObject): Call<MyReviewList>

    @Headers("Content-Type: application/json")
    @POST(API.MAIN_EVENT)
    fun mainevent(): Call<EventList>

    @Headers("Content-Type: application/json")
    @POST(API.EVENT_PAGE)
    fun eventpage(@Body jsonObject: JsonObject): Call<eventpage>

    @Headers("Content-Type: application/json")
    @POST(API.MAIN_CONTENTS)
    fun maincontents(): Call<ContentsList>

    @Headers("Content-Type: application/json")
    @POST(API.JOIN)
    fun join(@Body jsonObject: JsonObject): Call<Join>

    @Headers("Content-Type: application/json")
    @POST(API.LOGIN)
    fun login(@Body jsonObject: JsonObject): Call<Login>

    @Headers("Content-Type: application/json")
    @POST(API.LIKE_ON)
    fun likeOn(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.LIKE_OFF)
    fun likeOff(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.LIKE_LIST)
    fun likelist(@Body jsonObject: JsonObject): Call<Bookmark>

    @Headers("Content-Type: application/json")
    @POST(API.LOGINOUT)
    fun logout(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.DATEIL_STDIO)
    fun hotpickdata(@Body jsonObject: JsonObject): Call<datail>

    @Headers("Content-Type: application/json")
    @POST(API.STUDIO)
    fun studio(@Body jsonObject: JsonObject): Call<Studio>

    @Headers("Content-Type: application/json")
    @POST(API.PHOTOGRAPHER)
    fun photographer(@Body jsonObject: JsonObject): Call<Photo>

    @Headers("Content-Type: application/json")
    @POST(API.MODEL)
    fun model(@Body jsonObject: JsonObject): Call<Model>

    @Headers("Content-Type: application/json")
    @POST(API.RENT)
    fun rent(@Body jsonObject: JsonObject): Call<Rent>

    @Headers("Content-Type: application/json")
    @POST(API.BEAUTY)
    fun trip(@Body jsonObject: JsonObject): Call<Trip>

    @Headers("Content-Type: application/json")
    @POST(API.DATEIL_PHOTO)
    fun dateilphoto(@Body jsonObject: JsonObject): Call<dateilPhoto>

    @Headers("Content-Type: application/json")
    @POST(API.DATEIL_MODEL)
    fun dateilmodel(@Body jsonObject: JsonObject): Call<dateilModel>

    /**
     * 소품대여
     */

    @Headers("Content-Type: application/json")
    @POST(API.DATEIL_RENT)
    fun dateilrent(@Body jsonObject: JsonObject): Call<dateilRent>




    @Headers("Content-Type: application/json")
    @POST(API.DATEIL_TRIP)
    fun dateitrip(@Body jsonObject: JsonObject): Call<dateilTrip>

    @Headers("Content-Type: application/json")
    @POST(API.GUIDE_LIST)
    fun guidelist(): Call<Guide>

    @Headers("Content-Type: application/json")
    @POST(API.EDIT_USER)
    fun edituser(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.MYPAGE)
    fun mypage(@Body jsonObject: JsonObject): Call<Mypage>

    @Multipart
    @POST(API.EDIT_PROFILE)
    fun editProfile(
        @Part("idx") idx: String?,
        @Part("nickname") nickname: String?,
        @Part image: MultipartBody.Part
    ): Call<JsonElement>

    @Multipart
    @POST(API.EDIT_PROFILE)
    fun edittest(@PartMap params: HashMap<String, RequestBody>): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.INFORMATION)
    fun information(): Call<Information>

    @Multipart
    @POST(API.REQUEST)
    fun request(@PartMap params: HashMap<String, RequestBody>,
                @Part("expert_type") expert_type: Int?,
                @Part("expert_category") expert_category: Int?,
                @Part("place") place: Int?,
                @Part("request_dt_status") request_dt_status: Boolean? ): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.NOTICE)
    fun notice(): Call<Notice>

    @Headers("Content-Type: application/json")
    @POST(API.TOKEN)
    fun token(@Body jsonObject: JsonObject): Call<JsonElement>



    /*@Headers("Content-Type: application/json")
    @POST(API.PROGRESS_LIST)
    fun progresslist(@Body jsonObject: JsonObject): Call<ProgressList>*/
    /**
     * 1:1 요청
     */
    @Headers("Content-Type: application/json")
    @POST(API.RESERVE_STUDIO)
    fun reservestudio(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.RESERVE_EXPERT)
    fun reserveexpert(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.RESERVE_SHOP)
    fun reserverent(@Body jsonObject: JsonObject): Call<JsonElement>
    /**
     * 견적요청
     */

    //보낸견적
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_LIST)
    fun sendlist(@Body jsonObject: JsonObject): Call<SendList>

    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_STUDIO_PAGE)
    fun sendstudiopage(@Body jsonObject: JsonObject): Call<SendStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_EXPERT_PAGE)
    fun sendexpertpage(@Body jsonObject: JsonObject): Call<SendExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_SHOP_PAGE)
    fun sendshoppage(@Body jsonObject: JsonObject): Call<SendShopPage>

    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_PAGE)
    fun sendpage(@Body jsonObject: JsonObject): Call<Sendpage>


    //받은견적
    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_STUDIO_PAGE)
    fun receivestudiopage(@Body jsonObject: JsonObject): Call<ReceiveStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_EXPERT_PAGE)
    fun receiveexpertpage(@Body jsonObject: JsonObject): Call<ReceiveExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_SHOP_PAGE)
    fun receiveshoppage(@Body jsonObject: JsonObject): Call<ReceiveShopPage>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_LIST)
    fun receivelist(@Body jsonObject: JsonObject): Call<ReceiveList>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_MANY_PAGE)
    fun receivepage(@Body jsonObject: JsonObject): Call<Receivepage>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_PAYMENT)
    fun receivepayment(@Body jsonObject: JsonObject): Call<JsonElement>


    //진행현황
    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_LIST)
    fun progresslist(@Body jsonObject: JsonObject): Call<ProgressList>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_STUDIO_PAGE)
    fun progressstudiopage(@Body jsonObject: JsonObject): Call<ProgressStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_EXPERT_PAGE)
    fun progressexpertpage(@Body jsonObject: JsonObject): Call<ProgressExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_SHOP_PAGE)
    fun progressshoppage(@Body jsonObject: JsonObject): Call<ProgressShopPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_MANY_PAGE)
    fun progressmanypage(@Body jsonObject: JsonObject): Call<ProgressManyPage>



    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_RESERVE_COMPLETE)
    fun progressreservecomplete(@Body jsonObject: JsonObject): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_REQUEST_COMPLETE)
    fun progressrequestcomplete(@Body jsonObject: JsonObject): Call<JsonObject>

    /**
     * 환불요청
     */

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_RESERVE)
    fun refundreserve(@Body jsonObject: JsonObject): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_REQUEST)
    fun refundrequest(@Body jsonObject: JsonObject): Call<JsonObject>

    /**
     * 리뷰
     */

    @Headers("Content-Type: application/json")
    @GET(API.REVIEW_CALL)
    fun reviewcall(
        @Query("type") type: Int,
        @Query("reserve_idx") reserve_idx: String,
        @Query("request_idx") request_idx: String,
        @Query("request_log_idx") request_log_idx: String): Call<Review>

    @Multipart
    @POST(API.REVIEW_UPLOAD)
    fun reviewupload(@Part("type") type: Int,
                     @Part("reserve_idx") reserve_idx: RequestBody,
                     @Part("request_idx") request_idx: RequestBody,
                     @Part("request_log_idx") request_log_idx: RequestBody,
                     @Part("grade") grade: Int,
                     @Part("contents") contents: RequestBody,
                     @Part review_image: MultipartBody.Part? ): Call<JsonElement>

    /**
     * 채팅
     */

    @Headers("Content-Type: application/json")
    @GET(API.DETAIL_PAGE_CHAT)
    fun detailchat(
        @Query("user_idx") user_idx: Int,
        @Query("expert_type") expert_type: String,
        @Query("expert_idx") expert_idx: String,
        @Query("expert_user_idx") expert_user_idx: String): Call<ChatDatail>

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_DETAIL)
    fun chatdetail(@Body jsonObject: JsonObject): Call<Detail>

    @Multipart
    @POST(API.CHAT_IMAGE)
    fun chatimg(@Part("chat_idx") chat_idx: RequestBody,
                @Part chat_image: MultipartBody.Part): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_LOG)
    fun chatlog(@Body jsonObject: JsonObject): Single<ChatLog>

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_LIST)
    fun chatlist(@Body jsonObject: JsonObject): Call<ChatList>

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_OUT)
    fun chatout(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_REPORT_RESERVE)
    fun chatreportreserve(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_REPORT_REQUEST)
    fun chatreportrequest(@Body jsonObject: JsonObject): Call<JsonElement>

    /**
     * 예약 취소
     */

    @Headers("Content-Type: application/json")
    @POST(API.RESERVE_DELETE)
    fun reservedelete(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_DELETE)
    fun requestdelete(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVEPROGRESS)
    fun receiveprogress(@Body jsonObject: JsonObject): Call<ReceiveProgress>

    @Headers("Content-Type: application/json")
    @POST(API.DECISION)
    fun decision(@Body jsonObject: JsonObject): Call<JsonElement>

    /**
     * 알림
     */

    @Headers("Content-Type: application/json")
    @POST(API.NOTIFICATION_CHECK)
    fun notificationcheck(@Body jsonObject: JsonObject): Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST(API.NOTIFICATION_LIST)
    fun notificationlist(@Body jsonObject: JsonObject): Call<AlarmList>

    @Headers("Content-Type: application/json")
    @POST(API.NOTIFICATION_VIEW)
    fun notificationview(@Body jsonObject: JsonObject): Call<JsonElement>

    @Headers("Content-Type: application/json")
    @POST(API.NOTIFICATION_DELETE)
    fun notificationdelete(@Body jsonObject: JsonObject): Call<JsonElement>

}