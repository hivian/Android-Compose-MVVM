package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.data.remote.HttpResult
import com.hivian.lydia_test.core.models.dto.Results

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
