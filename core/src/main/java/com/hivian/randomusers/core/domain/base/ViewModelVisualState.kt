package com.hivian.randomusers.core.domain.base

import com.hivian.randomusers.core.data.remote.ErrorType

sealed class ViewModelVisualState {

    data object Loading: ViewModelVisualState()

    data class Error(val errorType: ErrorType): ViewModelVisualState()

    data object Success: ViewModelVisualState()

    data object Unknown: ViewModelVisualState()

}
