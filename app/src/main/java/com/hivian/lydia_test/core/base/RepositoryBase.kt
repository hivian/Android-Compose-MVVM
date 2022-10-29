package com.hivian.lydia_test.core.base

import com.hivian.lydia_test.core.services.networking.Resource
import com.hivian.lydia_test.core.services.networking.ResourceErrorType

open class RepositoryBase {

    suspend fun <T : Any> safeApiCall(call: suspend () -> T) : Resource<T> {
        return try {
            val response = call.invoke()
            Resource.Success(
                response
            )
        } catch (exception: Exception) {
            Resource.Error(ResourceErrorType.UNKNOWN)
        }
    }

}
