package com.hivian.compose_mvvm.presentation.home

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hivian.compose_mvvm.R
import com.hivian.compose_mvvm.presentation.base.ViewModelVisualState
import com.hivian.compose_mvvm.data.sources.remote.ErrorType
import com.hivian.compose_mvvm.domain.services.ILocalizationService
import com.hivian.compose_mvvm.domain.services.IUserInteractionService
import com.hivian.compose_mvvm.domain.models.RandomUser
import com.hivian.compose_mvvm.domain.repository.ServiceResult
import com.hivian.compose_mvvm.domain.usecases.GetRandomUsersUseCase
import com.hivian.compose_mvvm.presentation.base.PaginationViewModel
import com.hivian.compose_mvvm.presentation.services.navigation.INavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localizationService: ILocalizationService,
    private val navigationService: INavigationService,
    private val getRandomUsersUseCase: GetRandomUsersUseCase,
    private val userInteractionService: IUserInteractionService
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
            is ViewModelVisualState.Error -> errorTypeToErrorMessage(state.errorType)
            else -> ""
        }

    val retryMessage: String = localizationService.localizedString(R.string.retry_message)

    override fun initialize() {
        if (isInitialized.value == true) return

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
                userInteractionService.showSnackbar(
                    SnackbarDuration.Short,
                    errorTypeToErrorMessage(errorType)
                )
            }
        }
    }

    fun openRandomUserDetail(userId: Int) {
        navigationService.openRandomUserDetail(userId)
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

    private fun errorTypeToErrorMessage(errorType: ErrorType): String {
        return when(errorType) {
            ErrorType.ACCESS_DENIED -> localizationService.localizedString(R.string.error_access_denied)
            ErrorType.CANCELLED -> localizationService.localizedString(R.string.error_cancelled)
            ErrorType.HOST_UNREACHABLE -> localizationService.localizedString(R.string.error_no_connection)
            ErrorType.TIMED_OUT -> localizationService.localizedString(R.string.error_timeout)
            ErrorType.NO_RESULT -> localizationService.localizedString(R.string.error_not_found)
            ErrorType.UNKNOWN -> localizationService.localizedString(R.string.error_unknown)
        }
    }

}
