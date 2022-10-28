package com.hivian.lydia_test

import android.app.Application
import com.hivian.lydia_test.core.services.database.DatabaseService
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.localization.ILocalizationService
import com.hivian.lydia_test.core.services.localization.LocalizationService
import com.hivian.lydia_test.core.services.networking.IHttpClient
import com.hivian.lydia_test.core.services.networking.RetrofitHttpClient
import com.talentsoft.android.toolkit.core.IoC
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        IoC.registerSingleton<ILocalizationService> { LocalizationService(this) }
        IoC.registerSingleton<IHttpClient> { RetrofitHttpClient("https://randomuser.me") }
        IoC.registerSingleton<IDatabaseService> { DatabaseService(this) }
    }

}
