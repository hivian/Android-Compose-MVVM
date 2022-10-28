package com.hivian.lydia_test.core.services.base

import com.github.ajalt.timberkt.e
import com.hivian.lydia_test.core.services.networking.Resource

open class RepositoryBase {

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
