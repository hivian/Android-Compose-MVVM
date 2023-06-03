package com.hivian.compose_mvvm.core.base

import com.hivian.compose_mvvm.core.datasources.remote.ErrorType

sealed class ViewModelVisualState {

    object Loading: ViewModelVisualState()

    data class Error(val errorType: ErrorType): ViewModelVisualState()

    object Success: ViewModelVisualState()

    object Unknown: ViewModelVisualState()

}
