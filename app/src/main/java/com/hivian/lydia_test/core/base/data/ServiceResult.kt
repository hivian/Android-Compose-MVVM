package com.hivian.lydia_test.core.base.data

sealed class ServiceResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : ServiceResult<T>()

    data class Error<out T : Any>(val errorType: ResourceErrorType, val data: T) : ServiceResult<T>()

}
