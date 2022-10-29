package com.hivian.lydia_test.presentation.home

import androidx.lifecycle.*
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.models.Mapper
import com.hivian.lydia_test.presentation.ViewModelVisualState
import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.services.application.RandomUsersService
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.application.IScrollMoreDelegate
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.services.networking.HttpResult
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

    private var pageCount = 1

    private val randomUsersRepository = RandomUsersService()

    private var isLoadingMore: Boolean = false

    var title : String = localizationService.localizedString(R.string.home_fragment_title)

    var data : LiveData<List<RandomUserDomain>> =
        Transformations.map(randomUsersRepository.randomsUsersLocal) {
            if (it.isNotEmpty()) {
                pageCount = it.count() / RESULT_COUNT
            }

            Mapper.mapDTOToDomain(it)
        }

    var displayErrorMessage: LiveData<Boolean> = Transformations.map(data) {
            it.isEmpty() && viewModelVisualState.value is ViewModelVisualState.Error
        }

    val errorMessage : LiveData<String> = Transformations.map(viewModelVisualState) {
        when (it) {
            is ViewModelVisualState.Error -> localizationService.localizedString(R.string.error_message)
            else -> null
        }
    }

    init {
        fetchRandomUsers()
    }

    fun openRandomUserDetail(randomUser: RandomUserDomain) {
        navigationService.openRandomUserDetail(randomUser)
    }

    private fun fetchRandomUsers() = viewModelScope.launch {
        viewModelVisualState.value = ViewModelVisualState.Loading

        when (val result = randomUsersRepository.fetchRandomUsers(pageCount, RESULT_COUNT)) {
            is HttpResult.Success -> viewModelVisualState.value = ViewModelVisualState.Success
            is HttpResult.Error -> viewModelVisualState.value = ViewModelVisualState.Error(result.toVisualStateError())
        }
    }

    override fun loadMore() {
        if (isLoadingMore) return

        isLoadingMore = true
        viewModelScope.launch(Dispatchers.Main) {
            val resultList = randomUsersRepository.fetchRandomUsers(++pageCount, RESULT_COUNT)
            if (resultList is HttpResult.Error) {
                pageCount--
            }

            isLoadingMore = false
        }
    }
}
