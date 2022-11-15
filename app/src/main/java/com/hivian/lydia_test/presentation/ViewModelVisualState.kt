package com.hivian.lydia_test.presentation

import com.hivian.lydia_test.core.base.data.ResourceErrorType

sealed class ViewModelVisualState {

    object Loading: ViewModelVisualState()

    data class Error(val errorType: ResourceErrorType): ViewModelVisualState()

    object Success: ViewModelVisualState()

    object Unknown: ViewModelVisualState()

}
