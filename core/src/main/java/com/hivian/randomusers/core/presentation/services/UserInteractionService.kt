package com.hivian.randomusers.core.presentation.services

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import com.hivian.randomusers.core.domain.services.IUserInteractionService

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
