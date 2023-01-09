package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.data.ErrorType
import com.hivian.lydia_test.core.data.remote.HttpResult
import com.hivian.lydia_test.core.data.remote.HttpStatusCode
import com.hivian.lydia_test.core.data.remote.IApiService
import com.hivian.lydia_test.core.models.dto.Results
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HttpClient @Inject constructor(
    private val service: IApiService
): IHttpClient {

    override suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results> {
        return safeApiCall {
            service.fetchRandomUsers(page = page, results = results)
        }
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> T) : HttpResult<T> {
        return try {
            val response = call.invoke()

            HttpResult.Success(response)
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> HttpResult.Error(ErrorType.HOST_UNREACHABLE)
                is CancellationException -> HttpResult.Error(ErrorType.CANCELLED)
                is SocketTimeoutException -> HttpResult.Error(ErrorType.TIMED_OUT)
                is HttpException -> {
                    HttpResult.Error(when (exception.code()) {
                        HttpStatusCode.unauthorized, HttpStatusCode.forbidden -> ErrorType.ACCESS_DENIED
                        HttpStatusCode.timedOut -> ErrorType.TIMED_OUT
                        HttpStatusCode.notFound -> ErrorType.NO_RESULT
                        else -> ErrorType.UNKNOWN
                    })
                }
                else -> HttpResult.Error(ErrorType.UNKNOWN)
            }
        }
    }

}