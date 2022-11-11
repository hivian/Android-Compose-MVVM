package com.hivian.lydia_test.core.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hivian.lydia_test.presentation.ViewModelVisualState

abstract class ViewModelBase: ViewModel() {

    var isInitialized = MutableLiveData(false)
        protected set

    val viewModelVisualState = mutableStateOf<ViewModelVisualState>(ViewModelVisualState.Unknown)

    open fun initialize() {}

}
