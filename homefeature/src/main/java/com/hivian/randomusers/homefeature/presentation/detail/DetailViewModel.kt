package com.hivian.randomusers.homefeature.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hivian.randomusers.core.domain.base.ViewModelBase
import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.core.domain.extensions.toErrorMessage
import com.hivian.randomusers.core.presentation.navigation.NavigationAction
import com.hivian.randomusers.homefeature.domain.usecases.GetRandomUserByIdUseCase
import com.hivian.randomusers.core.domain.usecases.ShowAppMessageUseCase
import com.hivian.randomusers.core.domain.usecases.TranslateResourceUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val userId: Int,
    private val translateResourceUseCase: TranslateResourceUseCase,
    private val getRandomUserByIdUseCase: GetRandomUserByIdUseCase,
    private val showAppMessageUseCase: ShowAppMessageUseCase,
): ViewModelBase() {

    val picture = mutableStateOf("")

    val name = mutableStateOf("")

    val email = mutableStateOf("")

    val cell = mutableStateOf("")

    val phone = mutableStateOf("")

    val city = mutableStateOf("")

    val country = mutableStateOf("")

    val latitude = mutableStateOf(0.0)

    val longitude = mutableStateOf(0.0)

    override fun initialize() {
        if (isInitialized.value) return

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
                    city.value =result.data.address.city
                    country.value = result.data.address.country
                    latitude.value = result.data.address.latitude
                    longitude.value = result.data.address.longitude
                }
            }
        }

        isInitialized.value = true
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationAction.Back)
        }
    }

}
