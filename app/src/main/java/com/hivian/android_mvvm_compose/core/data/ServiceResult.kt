package com.hivian.android_mvvm_compose.core.data

import com.hivian.android_mvvm_compose.core.data.network.ErrorType

sealed class ServiceResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : ServiceResult<T>()

    data class Error<out T : Any>(val errorType: ErrorType, val data: T) : ServiceResult<T>()

}
