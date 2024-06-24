package com.hivian.randomusers.core.domain.services

import com.hivian.randomusers.core.data.models.Results
import com.hivian.randomusers.core.data.remote.ErrorType
import com.hivian.randomusers.core.data.remote.HttpResult
import com.hivian.randomusers.core.data.remote.HttpStatusCode
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

interface IApiService: IHttpService {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}

interface IHttpService {

    suspend fun <T : Any> safeApiCall(call: suspend () -> T) : HttpResult<T> {
        return try {
            val response = call.invoke()

            HttpResult.Success(response)
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> HttpResult.Error(ErrorType.HOST_UNREACHABLE)
                is CancellationException -> HttpResult.Error(ErrorType.CANCELLED)
                is SocketTimeoutException -> HttpResult.Error(ErrorType.TIMED_OUT)
                is HttpException -> {
                    HttpResult.Error(
                        when (exception.code()) {
                            HttpStatusCode.unauthorized, HttpStatusCode.forbidden -> ErrorType.ACCESS_DENIED
                            HttpStatusCode.notFound -> ErrorType.NO_RESULT
                            HttpStatusCode.timedOut -> ErrorType.TIMED_OUT
                            else -> ErrorType.UNKNOWN
                        }
                    )
                }
                else -> HttpResult.Error(ErrorType.UNKNOWN)
            }
        }
    }

}