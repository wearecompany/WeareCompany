
package com.weare.wearecompany.utils

import com.weare.wearecompany.BuildConfig.*

object Constants {
    const val TAG: String = "로그"
}

enum class RESPONSE_STATUS {
    OKAY,
    FAIL,
    NO_CONTENT,
    NOT_USER,
    LOGOUT_USER,
    NO_JOIN
}

enum class LIKE {
    OKAY,
    NOT_USER_AND_EXPERT,
    NOT_LIKE
}

enum class ESTIMATE {
    OKAY,
    NOT_USER
}

enum class ALARM {
    OKAY,
    NOT_CONTENT
}

object API {

    const val BASE_URL: String = SERVER_URL
    const val REFUND_URL = CHAT_URL
    const val PAYMENT_URL = CHAT_URL
    const val PAYMENT_ONECLICK_URL = PAYMENT_REQ_URL

    const val JOIN: String = "/A00/join"
    const val LOGIN: String = "/A00/login"
    const val LOGINOUT: String = "/C02/logout"

    const val EVENT_PAGE: String = "/A00/event/page"

    const val HOTPICK_LIST: String = "/A00/hotpick"

    const val MAIN: String = "/A00/home"
    const val MAIN_LIST: String = "/A00/home/list"
    const val MAIN_HOTPICK: String = "/A00/hotpick"
    const val MAIN_EVENT: String = "/A00/event"
    const val MAIN_CONTENTS: String = "/A00/contents"

    const val MYPAGE: String = "/A00/mypage"

    //전속모델
    const val DIVISION_LIST: String = "/A00/division/list"
    const val DIVISION_PAGE: String = "/A00/division/page"

    //changeRequest
    const val ONCLICK_CALL: String = "/A00/reserve/click"  //원클릭
    const val REQUEST_TOP_LIST: String = "/A00/reserve/list/top"    //결제된 top 리스트
    const val REQUEST_SEND_LIST: String = "/A00/reserve/list/0"    //보낸요청 리스트
    const val REQUEST_RECEIVE_LIST: String = "/A00/reserve/list/1"    //받은견적 리스트
    const val REQUEST_PROGRESS_LIST: String = "/A00/reserve/list/2"    //결제완료 리스트
    const val REQUEST_PROGRESS_OK_LIST: String = "/A00/reserve/list/3"    //진행완료 리스트
    const val REQUEST_REVIEW_LIST: String = "/A00/reserve/list/4"    //후기작성 리스트
    const val REQUEST_REFUND_LIST: String = "/A00/reserve/list/5"    //취소요청 리스트
    const val REQUEST_REFUND_OK_LIST: String = "/A00/reserve/list/6"    //취소완료 리스트


    const val REQUEST_STUDIO_PAGE: String = "/A00/reserve/page/studio/0"    //보낸요청 상세페이지(스튜디오)
    const val REQUEST_EXPERT_PAGE: String = "/A00/reserve/page/expert/0"    //보낸요청 상세페이지(전문가)
    const val REQUEST_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/0"    //보낸요청 상세페이지(원클릭)

    const val RECEIVE_STUDIO_PAGE: String = "/A00/reserve/page/studio/1"    //받은견적 상세페이지(스튜디오)
    const val RECEIVE_EXPERT_PAGE: String = "/A00/reserve/page/expert/1"    //받은견적 상세페이지(전문가)
    const val RECEIVE_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/1"    //받은견적 상세페이지(원클릭)

    const val PROGRESS_STUDIO_PAGE: String = "/A00/reserve/page/studio/2"    //결제완료 상세페이지(스튜디오)
    const val PROGRESS_EXPERT_PAGE: String = "/A00/reserve/page/expert/2"    //결제완료 상세페이지(전문가)
    const val PROGRESS_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/2"    //결제완료 상세페이지(원클릭)

    const val PROGRESS_OK_STUDIO_PAGE: String = "/A00/reserve/page/studio/3"    //진행완료 상세페이지(스튜디오)
    const val PROGRESS_OK_EXPERT_PAGE: String = "/A00/reserve/page/expert/3"    //진행완료 상세페이지(전문가)
    const val PROGRESS_OK_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/3"    //진행완료 상세페이지(원클릭)

    const val REVIEW_STUDIO_PAGE: String = "/A00/reserve/page/studio/4"    //후기작성 상세페이지(스튜디오)
    const val REVIEW_EXPERT_PAGE: String = "/A00/reserve/page/expert/4"    //후기작성 상세페이지(전문가)
    const val REVIEW_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/4"    //후기작성 상세페이지(원클릭)

    const val REFUND_STUDIO_PAGE: String = "/A00/reserve/page/studio/5"    //취소요청 상세페이지(스튜디오)
    const val REFUND_EXPERT_PAGE: String = "/A00/reserve/page/expert/5"    //취소요청 상세페이지(전문가)
    const val REFUND_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/5"    //취소요청 상세페이지(원클릭)

    const val REFUND_OK_STUDIO_PAGE: String = "/A00/reserve/page/studio/6"    //취소완료 상세페이지(스튜디오)
    const val REFUND_OK_EXPERT_PAGE: String = "/A00/reserve/page/expert/6"    //취소완료 상세페이지(전문가)
    const val REFUND_OK_ONECLICK_PAGE: String = "/A00/reserve/page/oneclick/6"    //취소완료 상세페이지(원클릭)


    //estimate
    const val RESERVE_STUDIO: String = "/A00/reserve/studio"   //1:1 스튜디오 예약요청
    const val RESERVE_EXPERT: String = "/A00/reserve/expert"   //1:1 전문가 예약요청

    const val TOKEN: String = "/A00/token"
    const val REQUEST_SHOP_PAGE: String = "/A00/reserve/shop/page/1"    //보낸요청 상세페이지(소품대요)
    const val REQUEST_LIST: String = "/A00/request/list"    //보낸요청 리스트

    const val RECEIVE_LIST: String = "/A00/response/list"    //받은요청 리스트
    const val RECEIVE_SHOP_PAGE: String = "/A00/reserve/shop/page/2"    //받은요청 상세페이지(소품대여)

    const val RECEIVE_ONE_PAYMENT: String = "/A00/request/bill"   //결제요청 (1:1)

    const val PROGRESS_LIST: String = "/A00/progress/list"    //견적진행 리스트

    const val PROGRESS_RESERVE_COMPLETE: String = "/A00/reserve/complete"    //구매확정 1:1

    const val RECEIVEPROGRESS: String = "/A00/response/page"
    const val DECISION: String = "/A00/response/decision"

    //list
    const val STUDIO: String = "/A00/studio_list"
    const val PHOTOGRAPHER: String = "/A00/photo_list"
    const val MODEL: String = "/A00/model_list"
    const val RENT: String = "/A00/rent_list"
    const val BEAUTY: String = "/A00/trip_list"

    //dateil
    const val DATEIL_STDIO: String = "/A00/studiopage"
    const val DATEIL_PHOTO: String = "/A00/photopage"
    const val DATEIL_MODEL: String = "/A00/modelpage"
    const val DATEIL_RENT: String = "/A00/rentpage"
    const val RESERVE_SHOP: String = "/A00/reserve/shop"
    const val DATEIL_TRIP: String = "/A00/trippage"

    const val GUIDE_LIST: String = "/A00/guide"

    //mypage
    const val EDIT_USER: String = "/C02/verify"
    const val EDIT_PROFILE: String = "/A00/edit_profile"
    const val INFORMATION: String = "/A00/manual"
    const val NOTICE: String = "/A00/notice"
    const val LIKE_ON: String = "/A00/like/on"
    const val LIKE_OFF: String = "/A00/like/off"
    const val LIKE_LIST: String = "/A00/like/list"

    //refund
    const val REFUND_RESERVE: String = "/A00/reserve/refund"
    const val REFUND_REQUEST: String = "/A00/reserve/refund"

    //review
    const val REVIEW_CALL: String = "/A00/review/regist"
    const val REVIEW_UPLOAD: String = "/A00/review/regist"

    //Cancellation
    const val RESERVE_DELETE: String = "/C02/reserve/delete"
    const val REQUEST_DELETE: String = "/C02/request/delete"


    //chatting
    const val DETAIL_PAGE_CHAT: String = "/A00/service/chat"
    const val CHAT_DETAIL: String = "/C02/chat/detail"
    const val CHAT_SERVICE_DETAIL: String = "/A00/service/chat"
    const val CHAT_IMAGE: String = "/C02/chat/img"
    const val CHAT_LOG: String = "/C02/chat/log"
    const val CHAT_LIST: String = "/A00/chat/list"
    const val CHAT_OUT: String = "/A00/chat/leave"
    const val CHAT_REPORT_RESERVE: String = "/C02/reserve/report"
    const val CHAT_REPORT_REQUEST: String = "/C02/request/report"

    //notification
    const val NOTIFICATION_CHECK: String = "/C02/alarm/status"
    const val NOTIFICATION_LIST: String = "/A00/alarm/list"
    const val NOTIFICATION_VIEW: String = "/C02/alarm/view"
    const val NOTIFICATION_DELETE: String = "/C02/alarm/delete"

    //번호인증
    const val CERT: String = "/C02/cert/main"
    const val CERT_CHECK: String = "/A00/cert"

    //후기 리스트
    const val MY_REVIEW_LIST: String = "/A00/review/list"


}