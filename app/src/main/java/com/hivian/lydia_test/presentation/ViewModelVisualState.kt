package com.hivian.lydia_test.presentation

import com.hivian.lydia_test.core.services.networking.ResourceErrorType

sealed class ViewModelVisualState {
    object Loading: ViewModelVisualState()
    data class Error(val errorType: ResourceErrorType): ViewModelVisualState()
    object Success: ViewModelVisualState()
    object Unknown: ViewModelVisualState()

    val isLoading : Boolean get() = this is Loading
    val isError : Boolean get() = this is Error
    val isSuccess : Boolean get() = this is Success
    val isUnknown : Boolean get() = this is Unknown
}
