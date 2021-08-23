package com.weare.wearecompany.ui.login

import android.content.Intent
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityLoginBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.join.JoinActivity
import com.weare.wearecompany.utils.ViewModelProviderFactory
import com.weare.wearecompany.MyApplication
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.weare.wearecompany.data.retrofit.MemberManager
import com.weare.wearecompany.utils.RESPONSE_STATUS
import timber.log.Timber
import java.security.MessageDigest


class login : BaseActivity<ActivityLoginBinding>(
    R.layout.activity_login,
    //LoginViewModel::class
) {
    private val Factory = ViewModelProviderFactory()

    var user_nickname : String = ""

    private val hotpickpostion = IntArray(2)

    private lateinit var email:String
    private lateinit var nickname:String
    private lateinit var uid:String
    private lateinit var image:String


    override fun onCreate() {

        //getAppKeyHash()

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        //Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.e("실패", "토큰 정보 보기 실패", error)
                    } else if (tokenInfo != null) {
                        Log.i(
                            "성공", "토큰 정보 보기 성공" +
                                    "\n회원번호: ${tokenInfo.id}" +
                                    "\n만료시간: ${tokenInfo.expiresIn} 초" +
                                    "\n리프레시토큰: ${token.refreshToken}" +
                                    "\n리프레시토큰: ${token.accessToken.length}"
                        )
                        name(token.refreshToken)

                    }
                }
            }

        }
        mViewDataBinding.kakaoLogin.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            UserApiClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@login)) {
                    loginWithKakaoTalk(this@login, callback = callback)
                } else {
                    loginWithKakaoAccount(this@login, callback = callback)
                }
            }


        }

        mViewDataBinding.notLogin.getLocationOnScreen(hotpickpostion)
        mViewDataBinding.notLogin.setOnClickListener {
            MyApplication.prefs.setString("user_idx","")
            var newIntent = Intent(this@login, ContainerActivity::class.java)
            startActivityForResult(newIntent,999)

        }
    }

    private fun getAppKeyHash() {
        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) {
            Log.e("name not found", e.toString())
        }
    }

    fun name(token: String) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
            } else if (user != null) {
                Timber.i("사용자 정보 요청 성공")
                uid = user.id.toString()
                email = user.kakaoAccount?.email.toString()
                image = user.kakaoAccount?.profile?.profileImageUrl.toString()
                nickname = user.kakaoAccount?.profile?.nickname.toString()
            }

            if (email != "") {
                MemberManager.instance.login(0, email, uid, completion = { responseStatus, arrayList ->
                    when (responseStatus) {
                        RESPONSE_STATUS.OKAY -> {
                            MyApplication.prefs.setString("user_idx", arrayList[0].user_idx)
                            var newIntent = Intent(this@login, ContainerActivity::class.java)
                            startActivity(newIntent)
                        }
                        RESPONSE_STATUS.NO_CONTENT -> {

                        }
                        RESPONSE_STATUS.NOT_USER -> {
                            //MyApplication.prefs.setString("user_idx",uid)
                            var newIntent = Intent(this@login, JoinActivity::class.java)
                            newIntent.putExtra("user_uid", uid)
                            newIntent.putExtra("user_email", email)
                            newIntent.putExtra("user_profile_image", image)
                            newIntent.putExtra("user_nickname", nickname)
                            newIntent.putExtra("token", token)
                            startActivity(newIntent)
                        }

                    }
                })
            } else {
                Toast.makeText(this, "이메일 정보를 불러오지 못해 가입이 불가능 합니다.",Toast.LENGTH_SHORT).show()
            }

        }
    }
}