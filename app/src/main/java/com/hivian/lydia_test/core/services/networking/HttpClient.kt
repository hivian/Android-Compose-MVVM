package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.base.data.ResourceErrorType
import com.hivian.lydia_test.core.base.data.remote.HttpResult
import com.hivian.lydia_test.core.base.data.remote.HttpStatusCode
import com.hivian.lydia_test.core.base.data.remote.IApiService
import com.hivian.lydia_test.core.models.dto.Results
import retrofit2.Response
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

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) : HttpResult<T> {
        return try {
            val response = call.invoke()

            when {
                response.isSuccessful && response.body() != null -> HttpResult.Success(response.body()!!)
                else -> {
                    HttpResult.Error(when (response.code()) {
                        HttpStatusCode.unauthorized, HttpStatusCode.forbidden -> ResourceErrorType.ACCESS_DENIED
                        HttpStatusCode.timedOut -> ResourceErrorType.TIMED_OUT
                        HttpStatusCode.notFound -> ResourceErrorType.NO_RESULT
                        else -> ResourceErrorType.UNKNOWN
                    })
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> HttpResult.Error(ResourceErrorType.HOST_UNREACHABLE)
                is CancellationException -> HttpResult.Error(ResourceErrorType.CANCELLED)
                is SocketTimeoutException -> HttpResult.Error(ResourceErrorType.TIMED_OUT)
                else -> HttpResult.Error(ResourceErrorType.UNKNOWN)
            }
        }
    }

}