package com.weare.wearecompany.ui.bottommenu.estimate.receive.payment

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.view.MenuItem
import android.webkit.*
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.BuildConfig.PAYMENT_RES_URL
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityPaymentBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.API
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.reflect.InvocationTargetException
import java.net.URISyntaxException
import java.net.URLDecoder
import java.net.URLEncoder


class paymentActivity:BaseActivity<ActivityPaymentBinding>(
    R.layout.activity_payment
) {

    companion object {
        const val MERCHANT_URL = "https://web.nicepay.co.kr/demo/v3/mobileReq.jsp"
    }

    private var request_idx:String = ""
    private var reserve_idx:String = ""
    private var request_log_idx:String = ""
    private var type:Int = -1

    private var testurl = "https://wearecompany.co.kr/A00/reserve/bill/oneclick/start"

    lateinit var socket: Socket

    val RESCODE = 1

    private var BANK_TID = ""
    private var NICE_BANK_URL  = ""
    @JavascriptInterface
    override fun onCreate() {

        type = intent.getIntExtra("type", 0)
        when(type) {
            0 -> {
                reserve_idx = intent.getStringExtra("reserve_idx").toString()
            }
            1 -> {
                reserve_idx = intent.getStringExtra("reserve_idx").toString()
                request_idx = intent.getStringExtra("request_idx").toString()
                request_log_idx = intent.getStringExtra("log_idx").toString()
            }
        }

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setUp()

    }



    fun activityCallback() {
        // 소켓 서버 연결
        socket = IO.socket(API.REFUND_URL)
        socket.connect()

        val `object2` = JSONObject()
        `object2`.put("type", type)
        `object2`.put("reserve_idx", reserve_idx)
        `object2`.put("request_idx", request_idx)
        `object2`.put("request_log_idx", request_log_idx)

        socket?.emit("notiUpdate", object2)

        socket.disconnect()

        val intent = Intent()
        intent.putExtra("payment", "ok")
        setResult(5001, intent)
        finish()
    }


    fun setUp() {
        mViewDataBinding.paymentWeb.settings.javaScriptEnabled = true   //자바 스크립트 활성화
        mViewDataBinding.paymentWeb.addJavascriptInterface(SubClass(), "payment")
        mViewDataBinding.paymentWeb.settings.cacheMode = WebSettings.LOAD_DEFAULT
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
                try {

                    if (url != null && url.startsWith("kftc-bankpay")) {
                        var reqParam =  makeBankPayData(url)
                        intent = Intent(Intent.ACTION_MAIN)
                        intent.component = ComponentName(
                            "com.kftc.bankpay.android",
                            "com.kftc.bankpay.android.activity.MainActivity"
                        )
                        intent.putExtra("requestInfo", reqParam)
                        this@paymentActivity.startActivityForResult(intent, RESCODE)
                    } else if( url != null && (url.startsWith("intent:")
                                || url.contains("market://")
                                || url.contains("vguard")
                                || url.contains("droidxantivirus")
                                || url.contains("v3mobile")
                                || url.contains(".apk")
                                || url.contains("mvaccine")
                                || url.contains("smartwall://")
                                || url.contains("http://m.ahnlab.com/kr/site/download")) ) {


                        var intent: Intent? = null


                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        } catch (e: URISyntaxException) {
                            //println("error : " + e.printStackTrace())
                        } catch (e: InvocationTargetException) {

                        }

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            if( view?.context?.packageManager?.resolveActivity(intent!!, 0) == null ) {
                                val pkgName = intent?.`package`
                                if (pkgName != null) {
                                    val uri = Uri.parse("market://details?id=" + pkgName)
                                    intent = Intent(Intent.ACTION_VIEW, uri)
                                    //intent.data = Uri.parse("payment://")
                                    startActivity(intent)
                                }
                            } else {
                                    val uri = Uri.parse(intent?.dataString)
                                    intent = Intent(Intent.ACTION_VIEW, uri)
                                    //intent.data = Uri.parse("payment://")
                                    startActivity(intent)
                                }
                            } else {
                                try {
                                    startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    val pkgName = intent?.`package`
                                    if (pkgName != null) {
                                        val uri = Uri.parse("market://details?id=" + pkgName)
                                        intent = Intent(Intent.ACTION_VIEW, uri)
                                        //intent.data = Uri.parse("payment://")
                                        startActivity(intent)
                                    }
                                }
                                catch (e: InvocationTargetException) {

                                }
                        }

                    } else if(url.startsWith("kftc-bankpay")){

                            val sub_str_param = "kftc-bankpay://eftpay?"
                            var reqParam: String? = url.substring(sub_str_param.length)
                            try {
                                reqParam = URLDecoder.decode(reqParam, "utf-8")
                            } catch (e: UnsupportedEncodingException) {
                                //e.printStackTrace()
                            }
                            catch (e: InvocationTargetException) {

                            }
                            reqParam = reqParam?.let { makeBankPayData(it) }
                            intent = Intent(Intent.ACTION_MAIN)
                            intent.component = ComponentName(
                                "com.kftc.bankpay.android",
                                "com.kftc.bankpay.android.activity.MainActivity"
                            )
                            intent.putExtra("requestInfo", reqParam)
                            startActivityForResult(intent, 1)


                    } else {
                        view?.loadUrl(url)
                    }
                } catch (e: Exception) {
                    //println("error : " + e.printStackTrace())
                    return false
                } catch (e: InvocationTargetException) {
                    return false
                }
                return true
            }
        }

        when(type) {
            0 -> {
                val str = "reserve_idx=" + URLEncoder.encode(reserve_idx, "UTF-8").toString()
                mViewDataBinding.paymentWeb.postUrl(
                    PAYMENT_RES_URL,
                    str.toByteArray()
                )
            }
            1 -> {
                val str = "reserve_idx=" + URLEncoder.encode(reserve_idx, "UTF-8").toString()
                    //.toString() + "&request_log_idx=" + URLEncoder.encode(request_log_idx, "UTF-8")
                //mViewDataBinding.paymentWeb.loadUrl("https://dev.wearecompany.co.kr/A00/request/bill")
                mViewDataBinding.paymentWeb.postUrl(
                    API.PAYMENT_ONECLICK_URL,
                    str.toByteArray()
                )
            }
        }
    }

    private fun makeBankPayData(str: String): String? {
        val arr = str.split("&".toRegex()).toTypedArray()
        var parse_temp: Array<String>
        val tempMap = HashMap<String, String>()
        for (i in arr.indices) {
            try {
                parse_temp = arr[i].split("=".toRegex()).toTypedArray()
                tempMap[parse_temp[0]] = parse_temp[1]
            } catch (e: java.lang.Exception) {
            } catch (e: InvocationTargetException) {
            }
        }
        BANK_TID = tempMap["user_key"].toString()
        NICE_BANK_URL = tempMap["callbackparam1"].toString()
        return str
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class  SubClass  {
        @JavascriptInterface
        fun sendMessage() {
            activityCallback()
        }
    }

}
