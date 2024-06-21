package com.hivian.compose_mvvm.core.services

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

internal class UserInteractionService: IUserInteractionService {

    override lateinit var snackbarHostState: SnackbarHostState

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

}
