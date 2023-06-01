package com.hivian.compose_mvvm.domain.services

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

interface IUserInteractionService {

    suspend fun showSnackbar(
        snackbarDuration: SnackbarDuration,
        message: String,
        actionTitle: String? = null
    )

    @Composable
    fun InitSnackBarHostState(snackbarHostState: SnackbarHostState)

}
