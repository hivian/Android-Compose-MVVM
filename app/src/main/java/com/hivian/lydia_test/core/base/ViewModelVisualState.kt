package com.hivian.lydia_test.core.base

import com.hivian.lydia_test.core.data.network.ErrorType

sealed class ViewModelVisualState {

    object Loading: ViewModelVisualState()

    data class Error(val errorType: ErrorType): ViewModelVisualState()

    object Success: ViewModelVisualState()

    object Unknown: ViewModelVisualState()

}
