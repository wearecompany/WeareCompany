package com.weare.wearecompany

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.weare.wearecompany.di.retrofitModule
import com.weare.wearecompany.utils.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class MyApplication: Application() {

    companion object{
        lateinit var instance: MyApplication
        private set
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()

        instance = this

        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        //Crashlytice가 Debug 모드에서는 작동하지 않도록 함
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(BuildConfig.DEBUG)

        startKoin{
            androidContext(this@MyApplication)

            modules(
                retrofitModule
            )
        }



    }

}