package com.hivian.compose_mvvm.data.services

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.hivian.compose_mvvm.domain.services.IUserInteractionService
import javax.inject.Inject

internal class UserInteractionService @Inject constructor(): IUserInteractionService {

    private lateinit var snackbarHostState: SnackbarHostState

    override suspend fun showSnackbar(
        snackbarDuration: SnackbarDuration,
        message: String,
        actionTitle: String?
    ) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionTitle,
            withDismissAction = !actionTitle.isNullOrEmpty(),
            duration = snackbarDuration,
        )
    }

    @Composable
    override fun InitSnackBarHostState(snackbarHostState: SnackbarHostState) {
        this.snackbarHostState = snackbarHostState
    }

}
