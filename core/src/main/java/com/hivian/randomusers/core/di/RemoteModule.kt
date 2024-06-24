package com.hivian.randomusers.core.di

import com.hivian.randomusers.core.data.remote.ApiService
import com.hivian.randomusers.core.data.remote.IHttpClient
import com.hivian.randomusers.core.data.remote.RemoteConst
import com.hivian.randomusers.core.domain.services.IApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(RemoteConst.TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(RemoteConst.TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(RemoteConst.TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(RemoteConst.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun provideHttpClient(retrofit: Retrofit): IHttpClient {
    return retrofit.create(IHttpClient::class.java)
}

private fun provideApiService(apiService: IHttpClient): IApiService {
    return ApiService(apiService)
}

val remoteModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofitClient(get()) }
    single<IHttpClient> { provideHttpClient(get()) }
    single<IApiService> { provideApiService(get()) }
}
