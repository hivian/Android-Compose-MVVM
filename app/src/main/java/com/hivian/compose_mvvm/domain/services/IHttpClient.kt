package com.hivian.compose_mvvm.domain.services

import com.hivian.compose_mvvm.data.sources.remote.HttpResult
import com.hivian.compose_mvvm.data.models.Results

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
