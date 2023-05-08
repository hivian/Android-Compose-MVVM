package com.hivian.lydia_test.presentation.home

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hivian.lydia_test.R
import com.hivian.lydia_test.core.base.ViewModelBase
import com.hivian.lydia_test.core.data.network.ErrorType
import com.hivian.lydia_test.core.data.paginator.DefaultPaginator
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.services.userinteraction.IUserInteractionService
import com.hivian.lydia_test.domain.mappers.ImageSize
import com.hivian.lydia_test.domain.mappers.mapToRandomUsers
import com.hivian.lydia_test.domain.models.RandomUser
import com.hivian.lydia_test.data.models.RandomUserDTO
import com.hivian.lydia_test.domain.services.application.IRandomUsersService
import com.hivian.lydia_test.presentation.ViewModelVisualState
import com.hivian.lydia_test.ui.services.navigation.INavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localizationService: ILocalizationService,
    private val navigationService: INavigationService,
    private val randomUsersService: IRandomUsersService,
    private val userInteractionService: IUserInteractionService
): ViewModelBase() {

    companion object {
        const val PAGINATOR_INITIAL_KEY = 1
        const val RESULT_COUNT = 20
    }

    var showLoadMoreLoader = mutableStateOf(false)

    var title : String = localizationService.localizedString(R.string.home_title)

    var items = mutableStateListOf<RandomUser>()

    private val paginator = DefaultPaginator(
        initialKey = PAGINATOR_INITIAL_KEY,
        getNextKey = { currentKey -> currentKey + 1 },
        onRequest = { nextPage -> randomUsersService.fetchRandomUsers(nextPage, RESULT_COUNT) },
        onLoading = { initialLoad ->
            if (initialLoad) {
                viewModelVisualState.value = ViewModelVisualState.Loading
                return@DefaultPaginator
            }

            showLoadMoreLoader.value = true
        },
        onError = { errorType, users, initialLoad ->
            if (initialLoad) {
                if (users.isNotEmpty()) {
                    updateData(users)
                    viewModelVisualState.value = ViewModelVisualState.Success
                } else {
                    viewModelVisualState.value = ViewModelVisualState.Error(errorType)
                }
            } else {
                if (users.isNotEmpty()) {
                    updateData(users)
                } else {
                    showLoadMoreLoader.value = false
                    userInteractionService.showSnackbar(
                        SnackbarDuration.Short,
                        errorTypeToErrorMessage(errorType)
                    )
                }
            }
        },
        onSuccess = { users, initialLoad ->
            updateData(users)
            if (users.isEmpty())
                showLoadMoreLoader.value = false

            if (initialLoad) {
                viewModelVisualState.value = ViewModelVisualState.Success
                return@DefaultPaginator
            }
        }

    )

    val errorMessage : String
        get() = when (val state = viewModelVisualState.value) {
            is ViewModelVisualState.Error -> errorTypeToErrorMessage(state.errorType)
            else -> ""
        }

    val retryMessage: String = localizationService.localizedString(R.string.retry_message)

    override fun initialize() {
        if (isInitialized.value == true) return

        loadNextItem()

        isInitialized.value = true
    }

    fun openRandomUserDetail(userId: Int) {
        navigationService.openRandomUserDetail(userId)
    }

    fun refresh() {
        paginator.reset()
        loadNextItem()
    }

    fun loadNextItem() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun updateData(users: List<RandomUserDTO>) {
        items.addAll(users.mapToRandomUsers(ImageSize.MEDIUM))
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
