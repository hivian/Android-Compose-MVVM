package com.hivian.compose_mvvm.core.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hivian.compose_mvvm.core.services.NavigationAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class ViewModelBase: ViewModel() {

    protected val _navigationEvent = MutableSharedFlow<NavigationAction?>()
    val navigationEvent: SharedFlow<NavigationAction?> = _navigationEvent.asSharedFlow()

    var isInitialized = mutableStateOf(false)
        protected set

    val viewModelVisualState = mutableStateOf<ViewModelVisualState>(ViewModelVisualState.Unknown)

    open fun initialize() {}

}
