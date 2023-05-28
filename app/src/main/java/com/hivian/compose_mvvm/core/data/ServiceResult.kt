package com.hivian.compose_mvvm.core.data

import com.hivian.compose_mvvm.core.data.network.ErrorType

sealed class ServiceResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : ServiceResult<T>()

    data class Error<out T : Any>(val errorType: ErrorType, val data: T) : ServiceResult<T>()

}
