package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.models.dto.Results
import com.hivian.lydia_test.core.remote.HttpStatusCode
import com.hivian.lydia_test.core.remote.IApiService
import com.hivian.lydia_test.core.remote.ResourceErrorType
import com.hivian.lydia_test.core.remote.ServiceResult
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HttpClient @Inject constructor(
    private val service: IApiService
): IHttpClient {

    override suspend fun fetchRandomUsers(page: Int, results: Int): ServiceResult<Results> {
        return safeApiCall {
            service.fetchRandomUsers(page = page, results = results)
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