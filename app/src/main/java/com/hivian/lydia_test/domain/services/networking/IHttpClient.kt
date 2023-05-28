package com.hivian.lydia_test.domain.services.networking

import com.hivian.lydia_test.core.data.network.HttpResult
import com.hivian.lydia_test.data.models.Results

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
