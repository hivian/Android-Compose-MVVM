package com.hivian.compose_mvvm.domain.services

import com.hivian.compose_mvvm.data.models.Results
import com.hivian.compose_mvvm.data.sources.remote.HttpResult

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
