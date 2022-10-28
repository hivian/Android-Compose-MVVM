package com.hivian.lydia_test

import android.app.Application
import com.hivian.lydia_test.core.localization.ILocalizationService
import com.hivian.lydia_test.core.localization.LocalizationService
import com.talentsoft.android.toolkit.core.IoC
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        IoC.registerSingleton<ILocalizationService> { LocalizationService(this) }
    }

}
