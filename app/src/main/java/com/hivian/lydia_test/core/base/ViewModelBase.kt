package com.hivian.lydia_test.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hivian.lydia_test.presentation.ViewModelVisualState

abstract class ViewModelBase: ViewModel() {

    val viewModelVisualState = MutableLiveData<ViewModelVisualState>()

}