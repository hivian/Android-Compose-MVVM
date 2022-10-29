package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.presentation.VisualStateErrorType

sealed class Resource<out T: Any> {

    data class Success<out T : Any>(val data: T) : Resource<T>()

    data class Error(val errorType: ResourceErrorType) : Resource<Nothing>() {

        fun toVisualStateError() = when (errorType) {
            ResourceErrorType.UNKNOWN -> VisualStateErrorType.UNKNOWN
        }

    }

}

enum class ResourceErrorType {
    UNKNOWN
}
