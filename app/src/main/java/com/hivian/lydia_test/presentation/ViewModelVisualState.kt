package com.hivian.lydia_test.presentation

import com.hivian.lydia_test.core.data.ErrorType

sealed class ViewModelVisualState {

    object Loading: ViewModelVisualState()

    data class Error(val errorType: ErrorType): ViewModelVisualState()

    object Success: ViewModelVisualState()

    object Unknown: ViewModelVisualState()

}
