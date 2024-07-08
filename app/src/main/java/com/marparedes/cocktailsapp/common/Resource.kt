package com.marparedes.cocktailsapp.common

import androidx.annotation.Keep

@Keep
data class Resource<out T>(
    val status: Status,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(status: Status): Resource<T> {
            return Resource(status, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data)
        }
    }
}
