package com.assessment.nanohealthsuite.service.network

import android.util.Log
import androidx.annotation.Keep
import retrofit2.Response

@Keep
abstract class BaseDataSource {
    private val logTag = "BaseDataSource"
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            Log.d(logTag, "Response: $response")

            if (response.isSuccessful) {
                val body = response.body()
                Log.d(logTag, "Response: $body")
                if (body != null) return Result.success(body)
            }

            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            e.printStackTrace()

            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Log.d(logTag, "Error received: $message")
        return Result.error("Request failed due to an unexpected response.")
    }


}
