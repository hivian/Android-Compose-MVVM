package com.hivian.randomusers.core.presentation.services

import android.content.Context
import androidx.annotation.StringRes
import com.hivian.randomusers.core.domain.services.ILocalizationService

internal class LocalizationService(
    private val context: Context
): ILocalizationService {

    override fun localizedString(@StringRes key: Int): String {
        return context.getString(key)
    }

    override fun localizedString(@StringRes key: Int, vararg arguments: Any): String {
        return context.getString(key, *arguments)
    }

}
