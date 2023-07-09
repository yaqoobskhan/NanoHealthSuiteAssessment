package com.assessment.nanohealthsuite.service.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.Throws

class RedirectInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        var response: Response = chain.proceed(chain.request())
        if (response.code() == 307) {
            request = request.newBuilder()
                .url(response.header("Location"))
                .build()
            response = chain.proceed(request)
        }
        return response
    }
}