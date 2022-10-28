package com.hivian.common

import android.content.Context
import com.hivian.common.localization.ILocalizationService
import com.hivian.common.localization.LocalizationService
import com.talentsoft.android.toolkit.core.IoC

object Common {

    fun initialize(applicationContext: Context) {
        IoC.registerSingleton<ILocalizationService> { LocalizationService(applicationContext) }
    }

}