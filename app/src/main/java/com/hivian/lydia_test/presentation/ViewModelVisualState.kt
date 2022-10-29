package com.hivian.lydia_test.presentation

sealed class ViewModelVisualState {
    object Loading: ViewModelVisualState()
    data class Error(val errorType: VisualStateErrorType): ViewModelVisualState()
    object Success: ViewModelVisualState()

    fun isLoading() = this is Loading
    fun isError() = this is Error
    fun isSuccess() = this is Success
}

enum class VisualStateErrorType {
    UNKNOWN
}
