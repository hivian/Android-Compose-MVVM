package com.hivian.lydia_test.core.base.data.remote

import com.hivian.lydia_test.core.base.data.ResourceErrorType

sealed class HttpResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : HttpResult<T>()

    data class Error<out T : Any>(val errorType: ResourceErrorType) : HttpResult<T>()

}
