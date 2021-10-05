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
import com.weare.wearecompany.data.chatting.ChatDatail
import com.weare.wearecompany.data.chatting.ChatList
import com.weare.wearecompany.data.chatting.ChatLog
import com.weare.wearecompany.data.chatting.Detail
import com.weare.wearecompany.data.division.Division
import com.weare.wearecompany.data.division.DivisionPage
import com.weare.wearecompany.data.login.Login
import com.weare.wearecompany.data.main.*
import com.weare.wearecompany.data.main.Request.*
import com.weare.wearecompany.data.main.Request.deta.sendoneclickpage
import com.weare.wearecompany.data.main.Request.deta.top
import com.weare.wearecompany.data.main.data.eventpage
import com.weare.wearecompany.data.notification.AlarmList
import com.weare.wearecompany.data.studio.main.Main
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun mainhotpick(): Single<HotpickList>

    @Headers("Content-Type: application/json")
    @POST(API.MY_REVIEW_LIST)
    fun myreviewlist(@Body jsonObject: JsonObject): Call<MyReviewList>

    @Headers("Content-Type: application/json")
    @POST(API.MAIN_EVENT)
    fun mainevent(): Single<EventList>

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

    @Headers("Content-Type: application/json")
    @POST(API.NOTICE)
    fun notice(): Call<Notice>

    @Headers("Content-Type: application/json")
    @POST(API.TOKEN)
    fun token(@Body jsonObject: JsonObject): Call<JsonElement>

    /**
     * 전속모델 리스트
     */
    @Headers("Content-Type: application/json")
    @GET(API.DIVISION_LIST)
    fun divisionlist(): Call<Division>

    @Headers("Content-Type: application/json")
    @GET(API.DIVISION_PAGE)
    fun divisionpage( @Query("user_idx") user_idx: String,
                      @Query("model_idx") model_idx: String): Call<DivisionPage>



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
     * 변경된 견적요청
     */

    @Multipart
    @POST(API.ONCLICK_CALL)
    fun oneclickcall(@PartMap params: HashMap<String, RequestBody>,
                @Part("expert_type") expert_type: List<Int>,
                @Part("price") price: Int ): Call<JsonElement>

    //보낸요청
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_TOP_LIST)
    fun requesttopList(@Body jsonObject: JsonObject): Call<TopList>

    //보낸요청
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_SEND_LIST)
    fun requestSendList(@Body jsonObject: JsonObject): Call<requestSendList>

    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_ONECLICK_PAGE)
    fun sendonclickpage(@Body jsonObject: JsonObject): Call<sendoneclickpage>

    //받은견적
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_RECEIVE_LIST)
    fun requestReceiveList(@Body jsonObject: JsonObject): Call<requestReceiveList>

    @Headers("Content-Type: application/json")
    @POST(API.RECEIVE_ONECLICK_PAGE)
    fun receiveonclickpage(@Body jsonObject: JsonObject): Call<receiveOneClickPage>

    //결제완료
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_PROGRESS_LIST)
    fun requestProgressList(@Body jsonObject: JsonObject): Call<requestReceiveList>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_STUDIO_PAGE)
    fun progressstudiopage(@Body jsonObject: JsonObject): Call<ProgressStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_EXPERT_PAGE)
    fun progressexpertpage(@Body jsonObject: JsonObject): Call<ProgressExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_ONECLICK_PAGE)
    fun progressonclickList(@Body jsonObject: JsonObject): Call<progressOneClickPage>

    //진행완료
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_PROGRESS_OK_LIST)
    fun requestProgressOkList(@Body jsonObject: JsonObject): Call<requestReceiveList>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_OK_STUDIO_PAGE)
    fun progressokstudiopage(@Body jsonObject: JsonObject): Call<ProgressStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_OK_EXPERT_PAGE)
    fun progressokexpertpage(@Body jsonObject: JsonObject): Call<ProgressExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_OK_ONECLICK_PAGE)
    fun progressokoneclickpage(@Body jsonObject: JsonObject): Call<progressOneClickPage>

    //구매확정
    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_RESERVE_COMPLETE)
    fun progressreservecomplete(@Body jsonObject: JsonObject): Call<JsonElement>

    //후기작성
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_REVIEW_LIST)
    fun requestReviewList(@Body jsonObject: JsonObject): Call<requestReviewList>

    @Headers("Content-Type: application/json")
    @POST(API.REVIEW_EXPERT_PAGE)
    fun reviewexpertpage(@Body jsonObject: JsonObject): Call<ProgressExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.REVIEW_STUDIO_PAGE)
    fun reviewstudiopage(@Body jsonObject: JsonObject): Call<ProgressStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.REVIEW_ONECLICK_PAGE)
    fun reviewoneclickpage(@Body jsonObject: JsonObject): Call<progressOneClickPage>

    //취소요청
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_REFUND_LIST)
    fun requestRefundList(@Body jsonObject: JsonObject): Call<requestReceiveList>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_EXPERT_PAGE)
    fun refundexpertpage(@Body jsonObject: JsonObject): Call<ProgressExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_STUDIO_PAGE)
    fun refundstudiopage(@Body jsonObject: JsonObject): Call<ProgressStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_ONECLICK_PAGE)
    fun refundoneclickpage(@Body jsonObject: JsonObject): Call<progressOneClickPage>

    //취소완료
    @Headers("Content-Type: application/json")
    @POST(API.REQUEST_REFUND_OK_LIST)
    fun requestRefundOkList(@Body jsonObject: JsonObject): Call<requestReceiveList>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_OK_EXPERT_PAGE)
    fun refundokexpertpage(@Body jsonObject: JsonObject): Call<ProgressExpertPage>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_OK_STUDIO_PAGE)
    fun refundokstudiopage(@Body jsonObject: JsonObject): Call<ProgressStudioPage>

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_OK_ONECLICK_PAGE)
    fun refundokoneclickpage(@Body jsonObject: JsonObject): Call<progressOneClickPage>


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


    //진행현황
    @Headers("Content-Type: application/json")
    @POST(API.PROGRESS_LIST)
    fun progresslist(@Body jsonObject: JsonObject): Call<ProgressList>


    /**
     * 환불요청
     */

    @Headers("Content-Type: application/json")
    @POST(API.REFUND_RESERVE)
    fun refundreserve(@Body jsonObject: JsonObject): Call<JsonElement>

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
        @Query("reserve_idx") reserve_idx: String ): Call<Review>

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

    @Headers("Content-Type: application/json")
    @POST(API.CHAT_SERVICE_DETAIL)
    fun chatservicedetail(@Body jsonObject: JsonObject): Call<ChatDatail>

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