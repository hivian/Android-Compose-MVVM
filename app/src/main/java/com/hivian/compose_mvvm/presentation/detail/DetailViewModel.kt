package com.hivian.compose_mvvm.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hivian.compose_mvvm.domain.repository.ServiceResult
import com.hivian.compose_mvvm.domain.usecases.GetRandomUserByIdUseCase
import com.hivian.compose_mvvm.domain.usecases.NavigateBackUseCase
import com.hivian.compose_mvvm.domain.usecases.ShowAppMessageUseCase
import com.hivian.compose_mvvm.domain.usecases.TranslateResourceUseCase
import com.hivian.compose_mvvm.presentation.base.ViewModelBase
import com.hivian.compose_mvvm.presentation.di.UserId
import com.hivian.compose_mvvm.presentation.extensions.toErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @UserId private val userId: Int,
    private val translateResourceUseCase: TranslateResourceUseCase,
    private val getRandomUserByIdUseCase: GetRandomUserByIdUseCase,
    private val showAppMessageUseCase: ShowAppMessageUseCase,
    private val navigateBackUseCase: NavigateBackUseCase
): ViewModelBase() {

    val picture = mutableStateOf("")

    val name = mutableStateOf("")

    val email = mutableStateOf("")

    val cell = mutableStateOf("")

    val phone = mutableStateOf("")

    override fun initialize() {
        if (isInitialized.value == true) return

        viewModelScope.launch {
            when (val result = getRandomUserByIdUseCase(userId)) {
                is ServiceResult.Error -> {
                    showAppMessageUseCase(
                        translateResourceUseCase(result.errorType.toErrorMessage())
                    )
                }
                is ServiceResult.Success -> {
                    picture.value = result.data.picture
                    name.value = result.data.fullName
                    email.value = result.data.email
                    cell.value = result.data.cell
                    phone.value = result.data.phone
                }
            }
        }

        isInitialized.value = true
    }

    fun navigateBack() {
        navigateBackUseCase()
    }

}
