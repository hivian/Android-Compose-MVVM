package com.hivian.randomusers.core.domain.services

import androidx.annotation.StringRes

interface ILocalizationService {

    fun localizedString(@StringRes key: Int): String

    fun localizedString(@StringRes key: Int, vararg arguments: Any): String

}
