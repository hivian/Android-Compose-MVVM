package com.hivian.lydia_test.core.services.networking

import com.hivian.lydia_test.core.models.dto.Results
import com.hivian.lydia_test.core.remote.ServiceResult

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): ServiceResult<Results>

}
