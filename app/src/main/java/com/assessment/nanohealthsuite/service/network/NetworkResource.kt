package com.assessment.nanohealthsuite.service.network

import com.assessment.nanohealthsuite.utility.Status


data class NetworkResource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): NetworkResource<T> =
            NetworkResource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): NetworkResource<T> =
            NetworkResource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): NetworkResource<T> =
            NetworkResource(status = Status.LOADING, data = data, message = null)
    }
}