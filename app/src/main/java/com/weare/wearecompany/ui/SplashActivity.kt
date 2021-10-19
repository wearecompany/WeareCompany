package com.weare.wearecompany.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.MemberManager
import com.weare.wearecompany.databinding.ActivitySplashBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.login.login
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.security.MessageDigest
import java.util.logging.Handler

class SplashActivity: BaseActivity<ActivitySplashBinding>(
    R.layout.activity_splash
) {

    private lateinit var email:String
    private lateinit var nickname:String
    private lateinit var uid:String
    private lateinit var image:String
    private var user_idx:String = ""
    private var move_data = ""

    override fun onCreate() {

        if (intent.data != null) {
            move_data = intent.data!!.getQueryParameter("data")!!
        }
        user_idx = MyApplication.prefs.getString("user_idx", "")

        Glide.with(this)
            .load(R.raw.splash_gif)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .into(mViewDataBinding.splashIcon2)

        /*try {
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
        } */

        android.os.Handler(Looper.getMainLooper()).postDelayed({

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
                            var newIntent = Intent(this, login::class.java)
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(newIntent)
                        }
                    }
                } else if (token != null) {
                    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                        if (error != null) {
                            var newIntent = Intent(this, login::class.java)
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(newIntent)
                        } else if (tokenInfo != null) {
                            name()
                        }
                    }
                }
            }

            if (user_idx != "") {
                name()
            } else {
                UserApiClient.instance.run {
                    if (isKakaoTalkLoginAvailable(this@SplashActivity)) {
                        loginWithKakaoTalk(this@SplashActivity, callback = callback)
                    } else {
                        loginWithKakaoAccount(this@SplashActivity, callback = callback)
                    }
                }
            }

        },2500)

    }

    fun name() {

        UserApiClient.instance.me { user, error ->
            if (user != null ) {
                uid = user.id.toString()
                email = user.kakaoAccount?.email.toString()
                image = user.kakaoAccount?.profile?.profileImageUrl.toString()
                nickname = user.kakaoAccount?.profile?.nickname.toString()
            } else if (error != null) {
                var newIntent = Intent(this@SplashActivity, login::class.java)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
            }

            MemberManager.instance.login(0,email, uid, completion = { responseStatus, arrayList ->
                when(responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        MyApplication.prefs.setString("user_idx",arrayList[0].user_idx)
                        var newIntent = Intent(this@SplashActivity, ContainerActivity::class.java)
                        newIntent.putExtra("move",move_data)
                        startActivity(newIntent)
                    }
                    RESPONSE_STATUS.NO_CONTENT -> {

                    }
                    RESPONSE_STATUS.NOT_USER -> {
                        //MyApplication.prefs.setString("user_idx",uid)
                        var newIntent = Intent(this@SplashActivity, login::class.java)
                        startActivity(newIntent)

                    }
                }
            })
        }
    }
}