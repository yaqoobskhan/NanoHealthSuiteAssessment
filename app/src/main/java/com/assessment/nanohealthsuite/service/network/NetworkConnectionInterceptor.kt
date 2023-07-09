package com.assessment.nanohealthsuite.service.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlin.Throws
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val mContext: Context
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    val isConnected: Boolean
        get() {
            val connectivityManager: ConnectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
            return netInfo != null && netInfo.isConnected()
        }

    init {
        mContext = context
    }
}