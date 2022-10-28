package com.hivian.lydia_test.core

import com.github.ajalt.timberkt.e

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> T) : Resource<T> {
        return try {
            val response = call.invoke()
            Resource.Success(
                response
            )
        } catch (exception: Exception) {
            e { "Network error: $exception" }
            Resource.Error("Network error")
        }
    }

}
