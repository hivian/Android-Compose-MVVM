package com.hivian_compose_mvvm.basic_feature.presentation.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hivian.compose_mvvm.core.base.ViewModelVisualState
import com.hivian.compose_mvvm.core.datasources.remote.ErrorType
import com.hivian_compose_mvvm.basic_feature.domain.models.Address
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser

data class DetailViewModelArg(
    val picture : MutableState<String>,
    val name : MutableState<String>,
    val email : MutableState<String>,
    val cell : MutableState<String>,
    val phone : MutableState<String>,
    val city : MutableState<String>,
    val country : MutableState<String>,
    val latitude : MutableState<Double>,
    val longitude : MutableState<Double>,
    val navigateBack: () -> Unit = {}
)

class DetailViewModelArgProvider: PreviewParameterProvider<DetailViewModelArg> {
    override val values: Sequence<DetailViewModelArg> = sequenceOf(
        DetailViewModelArg(
            picture = mutableStateOf(""),
            name = mutableStateOf("John Doe"),
            email = mutableStateOf("john.doe@mail.com"),
            cell = mutableStateOf("0606060606"),
            phone = mutableStateOf("0606060606"),
            city = mutableStateOf("Paris"),
            country = mutableStateOf("France"),
            latitude = mutableStateOf(0.0),
            longitude = mutableStateOf(0.0)
        )
    )
}
