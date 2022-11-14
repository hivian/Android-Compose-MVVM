package com.hivian.lydia_test.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.di.UserId
import com.hivian.lydia_test.core.models.ImageSize
import com.hivian.lydia_test.core.models.Mapper
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.navigation.INavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @UserId private val userId: Int,
    private val databaseService: IDatabaseService,
    private val navigationService: INavigationService
): ViewModelBase() {

    val picture = mutableStateOf("")

    val name = mutableStateOf("")

    val email = mutableStateOf("")

    val cell = mutableStateOf("")

    val phone = mutableStateOf("")

    override fun initialize() {
        if (isInitialized.value == true) return

        viewModelScope.launch {
            databaseService.getUserById(userId).let {
                val userDomain = Mapper.mapDTOToDomain(it, ImageSize.LARGE)
                picture.value = userDomain.picture
                name.value = userDomain.fullName
                email.value = userDomain.email
                cell.value = userDomain.cell
                phone.value = userDomain.phone
            }
        }

        isInitialized.value = true
    }

    fun navigateBack() {
        navigationService.navigateBack()
    }

}
