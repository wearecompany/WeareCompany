package com.weare.wearecompany.ui.bottommenu.estimate.receive.payment

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.BuildConfig.PHONE_CERT
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.ActivityPhotoPaymentBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.RESPONSE_STATUS
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URISyntaxException
import java.net.URLDecoder
import java.net.URLEncoder

class PhotoPaymentActivity:BaseActivity<ActivityPhotoPaymentBinding>(
    R.layout.activity_photo_payment
) {

    private var user_idx = ""

    @JavascriptInterface

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        user_idx = MyApplication.prefs.getString("user_idx", "")
        setUp()
        /*MainManager.instance.cert(user_idx,complation = {responseStatus ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                }
            }
        })*/

    }

    fun setUp() {

        mViewDataBinding.paymentWeb.settings.javaScriptEnabled = true   //자바 스크립트 활성화
        mViewDataBinding.paymentWeb.settings.domStorageEnabled = true   //로컬스토리지 이용 여부
        mViewDataBinding.paymentWeb.settings.javaScriptCanOpenWindowsAutomatically = true   // window.open() 허용 여부

        mViewDataBinding.paymentWeb.addJavascriptInterface(SubClass(), "phone")

        mViewDataBinding.paymentWeb.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        mViewDataBinding.paymentWeb.settings.loadsImagesAutomatically = true
        mViewDataBinding.paymentWeb.settings.builtInZoomControls = true
        mViewDataBinding.paymentWeb.settings.setSupportZoom(true)
        mViewDataBinding.paymentWeb.settings.setSupportMultipleWindows(true)
        mViewDataBinding.paymentWeb.settings.loadWithOverviewMode = true
        mViewDataBinding.paymentWeb.settings.useWideViewPort = true

        mViewDataBinding.paymentWeb.webChromeClient = WebChromeClient()

        if( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {    //Android 5.0 이상
            mViewDataBinding.paymentWeb.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            //mViewDataBinding.paymentWeb.webChromeClient = WebChromeClient()
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(
                mViewDataBinding.paymentWeb,
                true
            )
        } else {
            CookieManager.getInstance().setAcceptCookie(true)
        }

        mViewDataBinding.paymentWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                //웹뷰 내 표준창에서 외부앱(통신사 인증앱)을 호출하려면 intent:// URI를 별도로 처리해줘야 합니다.
                //다음 소스를 적용 해주세요.
                if (url.startsWith("intent://")) {
                    var intent : Intent? = null
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        if (intent != null) {
                            //앱실행
                            startActivity(intent);
                        }
                    } catch (e :URISyntaxException) {
                        //URI 문법 오류 시 처리 구간

                    } catch (e :ActivityNotFoundException) {
                           var packageName = intent?.getPackage();
                        if (!packageName.equals("")) {
                            // 앱이 설치되어 있지 않을 경우 구글마켓 이동
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                        }
                    }
                    //return  값을 반드시 true로 해야 합니다.
                    return true;

                } else if (url.startsWith("https://play.google.com/store/apps/details?id=com.sktelecom.tauth") || url.startsWith("market://details?id=com.sktelecom.tauth")) {    //SK PASS
                    var uri = Uri.parse(url);
                    var packageName = uri.getQueryParameter("id");
                    if (packageName != null && !packageName.equals("")) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    }
                    return true
                } else if (url.startsWith("https://play.google.com/store/apps/details?id=com.lguplus.smartotp") || url.startsWith("market://details?id=com.lguplus.smartotp")) {    //LG
                    // PASS
                    var uri = Uri.parse(url);
                    var packageName = uri.getQueryParameter("id");
                    if (packageName != null && !packageName.equals("")) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    }
                    return true
                }  else if (url.startsWith("https://play.google.com/store/apps/details?id=com.kt.ktauth") || url.startsWith("market://details?id=com.kt.ktauth")) {    //KT PASS
                    var uri = Uri.parse(url);
                    var packageName = uri.getQueryParameter("id");
                    if (packageName != null && !packageName.equals("")) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    }
                    return true
                }

                //return  값을 반드시 false로 해야 합니다.
                return false
            }


        }

        val str = "user_idx=" + URLEncoder.encode(user_idx, "UTF-8").toString()
        mViewDataBinding.paymentWeb.postUrl(
            PHONE_CERT,
            str.toByteArray()
        )
    }

    fun activityCallback(test2: Boolean) {

        when(test2) {
            true -> {
                Toast.makeText(this,"인증 성공",Toast.LENGTH_SHORT).show()
            finish()
            }
            else -> Toast.makeText(this,"인증 실패",Toast.LENGTH_SHORT).show()
        }

        /*val intent = Intent()
        intent.putExtra("payment", "ok")
        setResult(5001, intent)
        finish()*/
    }

    inner class  SubClass  {
        @JavascriptInterface
        fun phoneMessage(test: Boolean) {
            activityCallback(test)
        }
    }
}