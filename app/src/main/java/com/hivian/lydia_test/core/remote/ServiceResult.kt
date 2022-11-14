package com.hivian.lydia_test.core.remote

sealed class ServiceResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : ServiceResult<T>()

    data class Error(val errorType: ResourceErrorType) : ServiceResult<Nothing>()

}

enum class ResourceErrorType {
    ACCESS_DENIED,
    CANCELLED,
    HOST_UNREACHABLE,
    TIMED_OUT,
    NO_RESULT,
    UNKNOWN,
}
