package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.models.dto.Results
import com.hivian.lydia_test.core.remote.ApiService
import com.hivian.lydia_test.core.remote.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
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

    override suspend fun fetchRandomUsers(page: Int, results: Int): ServiceResult<Results> {
        return safeApiCall {
            retrofitService.fetchRandomUsers(page = page, results = results)
        }
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) : ServiceResult<T> {
        return try {
            val response = call.invoke()

            when {
                response.isSuccessful && response.body() != null -> ServiceResult.Success(response.body()!!)
                else -> {
                    ServiceResult.Error(when (response.code()) {
                        HttpStatusCode.unauthorized, HttpStatusCode.forbidden -> ResourceErrorType.ACCESS_DENIED
                        HttpStatusCode.timedOut -> ResourceErrorType.TIMED_OUT
                        HttpStatusCode.notFound -> ResourceErrorType.NO_RESULT
                        else -> ResourceErrorType.UNKNOWN
                    })
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> ServiceResult.Error(ResourceErrorType.HOST_UNREACHABLE)
                is CancellationException -> ServiceResult.Error(ResourceErrorType.CANCELLED)
                is SocketTimeoutException -> ServiceResult.Error(ResourceErrorType.TIMED_OUT)
                else -> ServiceResult.Error(ResourceErrorType.UNKNOWN)
            }
        }
    }

}