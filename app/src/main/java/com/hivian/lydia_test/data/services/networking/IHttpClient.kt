package com.hivian.lydia_test.data.services.networking

import com.hivian.lydia_test.core.data.network.HttpResult
import com.hivian.lydia_test.data.models.dto.Results

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
