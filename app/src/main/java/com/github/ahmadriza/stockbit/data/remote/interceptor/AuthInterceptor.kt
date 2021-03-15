package com.github.ahmadriza.stockbit.data.remote.interceptor

import android.content.Context
import com.github.ahmadriza.stockbit.BuildConfig
import com.github.ahmadriza.stockbit.data.local.SharedPreferenceHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by on 11/25/20.
 */
class AuthInterceptor @Inject constructor() :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val req = chain.request()
        val builder = req.newBuilder()

        builder.header("Authorization", "Apikey ${BuildConfig.API_KEY}")

        return chain.proceed(builder.build())
    }

}