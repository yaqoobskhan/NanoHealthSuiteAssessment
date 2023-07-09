package com.assessment.nanohealthsuite.service.network

import com.assessment.nanohealthsuite.BuildConfig
import com.assessment.nanohealthsuite.NanoHealthApplication
import com.assessment.nanohealthsuite.utility.Constant
import com.mklimek.sslutilsandroid.SslUtils
import javax.net.ssl.SSLContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    var baseUrl = Constant.BASE_URL
    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getTokenApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val sslContext: SSLContext =
            SslUtils.getSslContextForCertificateFile(NanoHealthApplication.appContext, "rma-ssl-cert.cer")
        val client = OkHttpClient().newBuilder()
            .retryOnConnectionFailure(true)
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
        client.sslSocketFactory(sslContext.socketFactory)
        client.addInterceptor(NetworkConnectionInterceptor(NanoHealthApplication.appContext))
        client.addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            client.addInterceptor(interceptor)
        }
        client.addInterceptor(RedirectInterceptor())
        return client.build()
    }

}

