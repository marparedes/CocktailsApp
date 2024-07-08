package com.marparedes.cocktailsapp.data.remote.api

import android.accounts.NetworkErrorException
import com.marparedes.cocktailsapp.common.Resource
import com.marparedes.cocktailsapp.common.Status
import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T> apiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    try {
        val response = apiCall()
        if(response.isSuccessful) {
            val body = response.body()
            return Resource.success(body)
        }
        return Resource.error(Status.API_ERROR)
    } catch (e: Exception) {
        return if (e is UnknownHostException || e is NetworkErrorException) {
            Resource.error(Status.NETWORK_ERROR)
        } else {
            Resource.error(Status.API_ERROR)
        }
    }
}