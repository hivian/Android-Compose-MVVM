package com.hivian.compose_mvvm

import android.app.Application
import com.hivian.compose_mvvm.core.di.localModule
import com.hivian.compose_mvvm.core.di.remoteModule
import com.hivian.compose_mvvm.core.di.coreServiceModule
import com.hivian_compose_mvvm.basic_feature.data.di.databaseServiceModule
import com.hivian_compose_mvvm.basic_feature.data.di.httpServiceModule
import com.hivian_compose_mvvm.basic_feature.data.di.repositoryModule
import com.hivian_compose_mvvm.basic_feature.domain.di.useCaseModule
import com.hivian_compose_mvvm.basic_feature.presentation.di.viewModelModule
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
                databaseServiceModule,
                httpServiceModule,
                coreServiceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }

}
