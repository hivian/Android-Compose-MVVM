package com.hivian.compose_mvvm.core.di

import com.hivian.compose_mvvm.core.datasources.remote.IApiService
import com.hivian.compose_mvvm.core.datasources.remote.RemoteConst
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(RemoteConst.TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(RemoteConst.TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(RemoteConst.TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}

fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(RemoteConst.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideApiService(retrofit: Retrofit): IApiService {
    return retrofit.create(IApiService::class.java)
}


val remoteModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofitClient(get()) }
    single { provideApiService(get()) }
}
