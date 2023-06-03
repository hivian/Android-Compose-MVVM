package com.hivian_compose_mvvm.basic_feature.domain.usecases

import androidx.compose.material3.SnackbarDuration
import com.hivian.compose_mvvm.core.services.IUserInteractionService
import javax.inject.Inject

class ShowAppMessageUseCase @Inject constructor(
    private val userInteractionService: IUserInteractionService
) {

    suspend operator fun invoke(message: String) {
        userInteractionService.showSnackbar(
            snackbarDuration = SnackbarDuration.Short,
            message = message
        )
    }

}
