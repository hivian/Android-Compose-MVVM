package com.hivian.randomusers

import android.app.Application
import com.hivian.randomusers.core.di.coreServiceModule
import com.hivian.randomusers.core.di.coreUseCaseModule
import com.hivian.randomusers.core.di.localModule
import com.hivian.randomusers.core.di.remoteModule
import com.hivian.randomusers.homefeature.di.repositoryModule
import com.hivian.randomusers.homefeature.di.useCaseModule
import com.hivian.randomusers.homefeature.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                localModule,
                remoteModule,
                coreServiceModule,
                repositoryModule,
                coreUseCaseModule,
                useCaseModule,
                viewModelModule
            )
        }
    }

}
