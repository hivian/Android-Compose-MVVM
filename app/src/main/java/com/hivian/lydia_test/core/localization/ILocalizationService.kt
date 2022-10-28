package com.hivian.lydia_test.core.localization

import androidx.annotation.StringRes


interface ILocalizationService {

    fun localizedString(@StringRes key: Int): String

    fun localizedString(@StringRes key: Int, vararg arguments: Any): String

}
