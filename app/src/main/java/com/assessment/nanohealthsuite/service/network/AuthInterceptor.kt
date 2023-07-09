package com.assessment.nanohealthsuite.service.network

import com.assessment.nanohealthsuite.NanoHealthApplication
import com.assessment.nanohealthsuite.utility.Constant
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val token = NanoHealthApplication.sharedPreferences.getString(Constant.TOKEN, "")
        val request = chain.request()
            val newRequest = request
                .newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        return chain.proceed(newRequest)
    }
}
