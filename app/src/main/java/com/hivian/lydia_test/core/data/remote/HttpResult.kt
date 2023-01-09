package com.hivian.lydia_test.core.data.remote

import com.hivian.lydia_test.core.data.ErrorType

sealed class HttpResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : HttpResult<T>()

    data class Error<out T : Any>(val errorType: ErrorType) : HttpResult<T>()

}