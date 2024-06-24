package com.hivian.randomusers.homefeature.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hivian.randomusers.core.data.remote.ErrorType
import com.hivian.randomusers.core.domain.base.ViewModelVisualState
import com.hivian.randomusers.homefeature.domain.models.Address
import com.hivian.randomusers.homefeature.domain.models.RandomUser

data class HomeViewModelArg(
    val randomUsers: SnapshotStateList<RandomUser>,
    val isListLoading: MutableState<Boolean>,
    val title: String,
    val errorMessage: String,
    val retryMessage: String,
    val visualState: MutableState<ViewModelVisualState> = mutableStateOf(ViewModelVisualState.Loading),
    val refresh: () -> Unit = {},
    val loadNext: () -> Unit = {},
    val openDetail: (Int) -> Unit = {},
)

class HomeViewModelArgProvider: PreviewParameterProvider<HomeViewModelArg> {
    override val values: Sequence<HomeViewModelArg> = sequenceOf(
        HomeViewModelArg(
            randomUsers = mutableStateListOf(),
            isListLoading = mutableStateOf(true),
            title = "Random Users",
            errorMessage = "Network Error",
            retryMessage = "Retry",
            visualState = mutableStateOf(ViewModelVisualState.Loading)
        ),
        HomeViewModelArg(
            randomUsers = mutableStateListOf(),
            isListLoading = mutableStateOf(false),
            title = "Random Users",
            errorMessage = "Network Error",
            retryMessage = "Retry",
            visualState = mutableStateOf(ViewModelVisualState.Error(ErrorType.UNKNOWN))
        ),
        HomeViewModelArg(
            randomUsers = populateList(),
            isListLoading = mutableStateOf(false),
            title = "Random Users",
            errorMessage = "Network Error",
            retryMessage = "Retry",
            visualState = mutableStateOf(ViewModelVisualState.Success)
        )
    )
}

private fun populateList(): SnapshotStateList<RandomUser> {
    val user = RandomUser(
        id = 1,
        gender = "male",
        firstName = "John",
        lastName = "Doe",
        email = "john.doe@mail.com",
        phone = "0606060606",
        cell = "0606060606",
        picture = "",
        address = Address(
            city = "Paris",
            state = "Paris",
            country = "France",
            latitude = 0.0,
            longitude = 0.0,
        )
    )

    val userList = mutableStateListOf<RandomUser>()
    repeat(10) {
        userList.add(user)
    }
    return userList
}


data class InitErrorViewArg(
    val errorMessage: String,
    val retryMessage: String,
)

class InitErrorViewArgProvider: PreviewParameterProvider<InitErrorViewArg> {
    override val values: Sequence<InitErrorViewArg> = sequenceOf(
        InitErrorViewArg(errorMessage = "Unknown error", retryMessage = "Retry")
    )
}