package com.hivian.lydia_test.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.models.ImageSize
import com.hivian.lydia_test.core.models.Mapper
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.servicelocator.IoC
import com.hivian.lydia_test.core.services.application.IRandomUsersService
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.core.services.networking.ResourceErrorType
import com.hivian.lydia_test.core.services.networking.ServiceResult
import com.hivian.lydia_test.presentation.ViewModelVisualState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModelBase() {

    companion object {
        const val RESULT_COUNT = 20
    }

    private val localizationService: ILocalizationService
        get() = IoC.resolve()

    private val navigationService: INavigationService
        get() = IoC.resolve()

    private val randomUsersService: IRandomUsersService
        get() = IoC.resolve()

    private var pageCount = 1

    private var isLoadingMore: Boolean = false

    var title : String = localizationService.localizedString(R.string.home_title)

    var items = mutableStateListOf<RandomUserDomain>()

    val errorMessage : String
        get() = when (val state = viewModelVisualState.value) {
            is ViewModelVisualState.Error -> when(state.errorType) {
                ResourceErrorType.ACCESS_DENIED -> localizationService.localizedString(R.string.error_access_denied)
                ResourceErrorType.CANCELLED -> localizationService.localizedString(R.string.error_cancelled)
                ResourceErrorType.HOST_UNREACHABLE -> localizationService.localizedString(R.string.error_no_connection)
                ResourceErrorType.TIMED_OUT -> localizationService.localizedString(R.string.error_timeout)
                ResourceErrorType.NO_RESULT -> localizationService.localizedString(R.string.error_not_found)
                ResourceErrorType.UNKNOWN -> localizationService.localizedString(R.string.error_unknown)
            }
            else -> ""
        }

    val retryMessage: String = localizationService.localizedString(R.string.retry_message)

    override fun initialize() {
        if (isInitialized.value == true) return

        fetchRandomUsers()

        isInitialized.value = true
    }

    fun openRandomUserDetail(userId: Int) {
        navigationService.openRandomUserDetail(userId)
    }

    fun refresh() {
        fetchRandomUsers()
    }

    private fun fetchRandomUsers() = viewModelScope.launch {
        viewModelVisualState.value = ViewModelVisualState.Loading

        when (val result = randomUsersService.fetchRandomUsers(pageCount, RESULT_COUNT)) {
            is ServiceResult.Success -> {
                updateData(result.data)
                viewModelVisualState.value = ViewModelVisualState.Success
            }
            is ServiceResult.Error -> viewModelVisualState.value = ViewModelVisualState.Error(result.errorType)
        }
    }

    fun loadMore() {
        if (isLoadingMore) return

        isLoadingMore = true
        viewModelScope.launch(Dispatchers.Main) {
            when (val resultList = randomUsersService.fetchRandomUsers(++pageCount, RESULT_COUNT)) {
                is ServiceResult.Error -> pageCount--
                is ServiceResult.Success -> updateData(resultList.data)
            }

            isLoadingMore = false
        }
    }

    private fun updateData(users: List<RandomUserDTO>) {
        if (users.isNotEmpty()) {
            pageCount = users.count() / RESULT_COUNT
        }

        items.addAll(Mapper.mapDTOToDomain(users, ImageSize.MEDIUM))
    }

}
