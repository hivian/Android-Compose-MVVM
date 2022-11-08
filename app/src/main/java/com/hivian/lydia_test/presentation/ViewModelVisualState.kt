package com.hivian.lydia_test.presentation

sealed class ViewModelVisualState {
    object Loading: ViewModelVisualState()
    data class Error(val errorType: VisualStateErrorType): ViewModelVisualState()
    object Success: ViewModelVisualState()
    object Unknown: ViewModelVisualState()

    val isLoading : Boolean get() = this is Loading
    val isError : Boolean get() = this is Error
    val isSuccess : Boolean get() = this is Success
    val isUnknown : Boolean get() = this is Unknown
}

enum class VisualStateErrorType {
    UNKNOWN
}
