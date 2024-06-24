package com.hivian.randomusers.core.domain.services

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

interface IUserInteractionService {

    var snackbarHostState: SnackbarHostState

    suspend fun showSnackbar(
        snackbarDuration: SnackbarDuration,
        message: String,
        actionTitle: String? = null
    )

}
