package com.hivian.lydia_test.presentation.home

import androidx.lifecycle.*
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.models.Mapper
import com.hivian.lydia_test.presentation.ViewModelVisualState
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.application.IScrollMoreDelegate
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.application.IRandomUsersService
import com.hivian.lydia_test.core.services.networking.ServiceResult
import com.hivian.lydia_test.core.services.navigation.INavigationService
import com.talentsoft.android.toolkit.core.IoC
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

    var data = MutableLiveData<List<RandomUserDomain>>()

    var displayErrorMessage: LiveData<Boolean> = Transformations.map(viewModelVisualState) {
        data.value.isNullOrEmpty() && viewModelVisualState.value is ViewModelVisualState.Error
    }

    val errorMessage : LiveData<String> = Transformations.map(viewModelVisualState) {
        when (it) {
            is ViewModelVisualState.Error -> localizationService.localizedString(R.string.error_message)
            else -> null
        }
    }

    val retryMessage: String = localizationService.localizedString(R.string.retry_message)

    init {
        fetchRandomUsers()
    }

    fun openRandomUserDetail(randomUser: RandomUserDomain) {
        navigationService.openRandomUserDetail(randomUser)
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
            val resultList = randomUsersService.fetchRandomUsers(++pageCount, RESULT_COUNT)

            if (resultList is ServiceResult.Error) {
                pageCount--
            }

            if (resultList is ServiceResult.Success) {
                updateData(resultList.data)
            }

            isLoadingMore = false
        }
    }

    private fun updateData(users: List<RandomUserDTO>) {
        if (users.isNotEmpty()) {
            pageCount = users.count() / RESULT_COUNT
        }

        data.value = Mapper.mapDTOToDomain(users)
    }

}
