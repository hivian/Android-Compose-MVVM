package com.hivian.compose_mvvm.data

import com.hivian.compose_mvvm.InstantExecutorExtension
import com.hivian.compose_mvvm.MainCoroutineExtension
import com.hivian.compose_mvvm.core.data.network.ErrorType
import com.hivian.compose_mvvm.core.data.network.HttpResult
import com.hivian.compose_mvvm.core.data.network.HttpStatusCode
import com.hivian.compose_mvvm.data.models.Results
import com.hivian.compose_mvvm.domain.services.networking.HttpClient
import com.hivian.compose_mvvm.domain.services.networking.IHttpClient
import com.hivian.compose_mvvm.data.sources.remote.IApiService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, MainCoroutineExtension::class)
class HttpClientTest {

    private val apiService = mock<IApiService>()

    private lateinit var httpClient: IHttpClient

    @BeforeEach
    fun setUp() {
        httpClient = HttpClient(apiService)
    }

    @Test
    fun `Http Client returns Success`() = runTest {
        val mockResults = Results(emptyList())

        whenever(
            apiService.fetchRandomUsers()
        ).thenReturn(
            mockResults
        )

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Success(mockResults), result)
    }

    @Test
    fun `Http Client returns Error - Host unreachable`() = runTest {
        whenever(
            apiService.fetchRandomUsers()
        ).thenThrow(
            UnknownHostException::class.java
        )

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.HOST_UNREACHABLE), result)
    }

    @Test
    fun `Http Client returns Error - Cancelled`() = runTest {
        whenever(
            apiService.fetchRandomUsers()
        ).thenThrow(
            CancellationException::class.java
        )

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.CANCELLED), result)
    }

    @Test
    fun `Http Client returns Error - Time out`() = runTest {
        whenever(
            apiService.fetchRandomUsers()
        ).thenThrow(
            SocketTimeoutException::class.java
        )

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.TIMED_OUT), result)
    }

    @Test
    fun `Http Client returns Error - Unknown`() = runTest {
        whenever(
            apiService.fetchRandomUsers()
        ).thenThrow(
            IOException::class.java
        )

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.UNKNOWN), result)
    }

    @Test
    fun `Http Client returns Http Error - code 401`() = runTest {
        mockHttpErrors(HttpStatusCode.unauthorized)

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.ACCESS_DENIED), result)
    }

    @Test
    fun `Http Client returns Http Error - code 403`() = runTest {
        mockHttpErrors(HttpStatusCode.forbidden)

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.ACCESS_DENIED), result)
    }

    @Test
    fun `Http Client returns Http Error - code 404`() = runTest {
        mockHttpErrors(HttpStatusCode.notFound)

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.NO_RESULT), result)
    }

    @Test
    fun `Http Client returns Http Error - code 408`() = runTest {
        mockHttpErrors(HttpStatusCode.timedOut)

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.TIMED_OUT), result)
    }

    @Test
    fun `Http Client returns Http Error - Unknown`() = runTest {
        mockHttpErrors(HttpStatusCode.internalError)

        val result = httpClient.fetchRandomUsers(1, 20)
        advanceUntilIdle()
        assertEquals(HttpResult.Error<Results>(ErrorType.UNKNOWN), result)
    }

    private suspend fun mockHttpErrors(code: Int) {
        whenever(
            apiService.fetchRandomUsers()
        ).thenThrow(
            HttpException(
                Response.error<Results>(
                    code,
                    "".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
        )
    }

}