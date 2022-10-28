package com.hivian.lydia_test

import android.app.Application
import com.hivian.common.Common
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Common.initialize(this)
    }

}
