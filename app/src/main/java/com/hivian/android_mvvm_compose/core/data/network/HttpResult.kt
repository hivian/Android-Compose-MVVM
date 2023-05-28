package com.hivian.android_mvvm_compose.core.data.network

sealed class HttpResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : HttpResult<T>()

    data class Error<out T : Any>(val errorType: ErrorType) : HttpResult<T>()

}
