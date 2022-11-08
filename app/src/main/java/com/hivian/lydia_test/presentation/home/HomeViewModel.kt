package com.hivian.lydia_test.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.application.IScrollMoreDelegate
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.models.Mapper
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.servicelocator.IoC
import com.hivian.lydia_test.core.services.application.IRandomUsersService
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.hivian.lydia_test.core.services.networking.ServiceResult
import com.hivian.lydia_test.presentation.ViewModelVisualState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModelBase(), IScrollMoreDelegate {

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

    var title : String = localizationService.localizedString(R.string.home_fragment_title)

    var items = mutableStateListOf<RandomUserDomain>()

    var displayErrorMessage: LiveData<Boolean> = Transformations.map(viewModelVisualState) {
        items.isEmpty() && viewModelVisualState.value is ViewModelVisualState.Error
    }

    val errorMessage : LiveData<String> = Transformations.map(viewModelVisualState) {
        when (it) {
            is ViewModelVisualState.Error -> localizationService.localizedString(R.string.error_message)
            else -> null
        }
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
            is ServiceResult.Error -> viewModelVisualState.value = ViewModelVisualState.Error(result.toVisualStateError())
        }
    }

    override fun loadMore() {
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

        items.addAll(Mapper.mapDTOToDomain(users))
    }

}
