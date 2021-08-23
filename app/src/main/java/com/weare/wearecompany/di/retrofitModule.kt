package com.weare.wearecompany.di

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.weare.wearecompany.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module

val retrofitModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            chain.proceed(original.newBuilder().apply {
                addHeader("Authorizaion_Header", "access_token")
            }.build())
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    /*single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(
            RetrofitInterface::class.java
        )
    }*/
}
