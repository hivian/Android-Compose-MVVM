package com.hivian.compose_mvvm.core.datasource.remote

import com.hivian.compose_mvvm.core.datasource.ErrorType
import com.hivian.compose_mvvm.core.datasource.HttpResult
import com.hivian.compose_mvvm.core.datasource.HttpStatusCode
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

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