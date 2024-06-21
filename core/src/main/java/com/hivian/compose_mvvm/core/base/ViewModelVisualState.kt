package com.hivian.compose_mvvm.core.base

import com.hivian.compose_mvvm.core.datasources.remote.ErrorType

sealed class ViewModelVisualState {

    data object Loading: ViewModelVisualState()

    data class Error(val errorType: ErrorType): ViewModelVisualState()

    data object Success: ViewModelVisualState()

    data object Unknown: ViewModelVisualState()

}
