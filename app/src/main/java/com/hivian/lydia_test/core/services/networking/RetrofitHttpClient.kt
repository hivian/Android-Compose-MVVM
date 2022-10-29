package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.remote.ApiService
import com.hivian.lydia_test.core.remote.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitHttpClient(baseUrl: String): IHttpClient {

    override var baseUrl: String = baseUrl
        set(value) {
            field = if (value.endsWith('/')) value else "$value/"
        }

    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = okHttpClient.newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
        builder.client(client).build()
    }

    private val retrofitService: ApiService = retrofit.create(ApiService::class.java)

    override suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<List<RandomUserDTO>> {
        return safeApiCall {
            retrofitService.fetchRandomUsers(page = page, results = results).results
        }
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> T) : HttpResult<T> {
        return try {
            val response = call.invoke()
            HttpResult.Success(
                response
            )
        } catch (exception: Exception) {
            HttpResult.Error(ResourceErrorType.UNKNOWN)
        }
    }

}