package com.hivian.randomusers.homefeature.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hivian.randomusers.core.R
import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.core.data.remote.ErrorType
import com.hivian.randomusers.core.domain.base.PaginationViewModel
import com.hivian.randomusers.core.domain.base.ViewModelVisualState
import com.hivian.randomusers.core.domain.extensions.toErrorMessage
import com.hivian.randomusers.core.domain.services.ILocalizationService
import com.hivian.randomusers.core.domain.usecases.ShowAppMessageUseCase
import com.hivian.randomusers.core.presentation.navigation.NavigationAction
import com.hivian.randomusers.homefeature.domain.models.RandomUser
import com.hivian.randomusers.homefeature.domain.usecases.GetRandomUsersUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val localizationService: ILocalizationService,
    private val getRandomUsersUseCase: GetRandomUsersUseCase,
    private val showAppMessageUseCase: ShowAppMessageUseCase
): PaginationViewModel<Int, RandomUser>(initialKey = PAGINATION_INITIAL_KEY, pageSize = RESULT_COUNT) {

    companion object {
        const val PAGINATION_INITIAL_KEY = 1
        const val RESULT_COUNT = 20
    }

    var showLoadMoreLoader = mutableStateOf(false)

    var title : String = localizationService.localizedString(R.string.home_title)

    var items = mutableStateListOf<RandomUser>()

    val errorMessage : String
        get() = when (val state = viewModelVisualState.value) {
            is ViewModelVisualState.Error -> {
                localizationService.localizedString(state.errorType.toErrorMessage())
            }
            else -> ""
        }

    val retryMessage: String = localizationService.localizedString(R.string.retry_message)


    override fun initialize() {
        if (isInitialized.value) return

        loadNext()

        isInitialized.value = true
    }

    override fun getNextKey(currentKey: Int): Int {
        return currentKey + 1
    }

    override suspend fun onRequest(nextKey: Int, pageSize: Int): ServiceResult<List<RandomUser>> {
        return getRandomUsersUseCase(nextKey, pageSize)
    }

    override fun onLoading(initialLoad: Boolean) {
        if (initialLoad) {
            viewModelVisualState.value = ViewModelVisualState.Loading
            return
        }

        showLoadMoreLoader.value = true
    }

    override fun onSuccess(users: List<RandomUser>, initialLoad: Boolean) {
        updateData(users)
        if (users.isEmpty())
            showLoadMoreLoader.value = false

        if (initialLoad) {
            viewModelVisualState.value = ViewModelVisualState.Success
        }
    }

    override fun onError(errorType: ErrorType, users: List<RandomUser>, initialLoad: Boolean) {
        if (initialLoad) {
            if (users.isNotEmpty()) {
                updateData(users)
                viewModelVisualState.value = ViewModelVisualState.Success
            } else {
                viewModelVisualState.value = ViewModelVisualState.Error(errorType)
            }
            return
        }

        if (users.isNotEmpty()) {
            updateData(users)
        } else {
            showLoadMoreLoader.value = false
            viewModelScope.launch {
                showAppMessageUseCase(
                    localizationService.localizedString(errorType.toErrorMessage())
                )
            }
        }
    }

    fun openRandomUserDetail(userId: Int) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationAction.ToDetailScreen(userId))
        }
    }

    fun refresh() {
        reset()
        loadNext()
    }

    fun loadNext() {
        viewModelScope.launch {
            loadNextItems()
        }
    }

    private fun updateData(users: List<RandomUser>) {
        items.addAll(users)
    }

}
