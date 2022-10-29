package com.hivian.lydia_test.core.services.localization

import android.content.Context
import androidx.annotation.StringRes


internal class LocalizationService(private val context: Context): ILocalizationService {

    override fun localizedString(@StringRes key: Int): String {
        return context.getString(key)
    }

    override fun localizedString(@StringRes key: Int, vararg arguments: Any): String {
        return context.getString(key, *arguments)
    }

}
