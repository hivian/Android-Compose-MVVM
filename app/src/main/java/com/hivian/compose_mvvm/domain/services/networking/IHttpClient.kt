package com.hivian.compose_mvvm.domain.services.networking

import com.hivian.compose_mvvm.core.data.network.HttpResult
import com.hivian.compose_mvvm.data.models.Results

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
