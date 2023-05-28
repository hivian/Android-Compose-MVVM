package com.hivian.android_mvvm_compose.domain.services.networking

import com.hivian.android_mvvm_compose.core.data.network.HttpResult
import com.hivian.android_mvvm_compose.data.models.Results

interface IHttpClient {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
