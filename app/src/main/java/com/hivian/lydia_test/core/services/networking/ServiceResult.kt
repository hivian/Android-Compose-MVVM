package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.presentation.VisualStateErrorType

sealed class ServiceResult<out T: Any> {

    data class Success<out T : Any>(val data: T) : ServiceResult<T>()

    data class Error(val errorType: ResourceErrorType) : ServiceResult<Nothing>() {

        fun toVisualStateError() = when (errorType) {
            ResourceErrorType.UNKNOWN -> VisualStateErrorType.UNKNOWN
        }

    }

}

enum class ResourceErrorType {
    UNKNOWN
}
