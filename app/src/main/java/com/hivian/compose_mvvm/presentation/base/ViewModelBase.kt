package com.hivian.compose_mvvm.presentation.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelBase: ViewModel() {

    var isInitialized = MutableLiveData(false)
        protected set

    val viewModelVisualState = mutableStateOf<ViewModelVisualState>(ViewModelVisualState.Unknown)

    open fun initialize() {}

}
