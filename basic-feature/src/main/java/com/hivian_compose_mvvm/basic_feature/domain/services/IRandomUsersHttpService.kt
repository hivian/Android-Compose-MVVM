package com.hivian_compose_mvvm.basic_feature.domain.services

import com.hivian.compose_mvvm.core.datasources.remote.HttpResult
import com.hivian.compose_mvvm.core.datasources.models.Results
import com.hivian.compose_mvvm.core.datasources.remote.IHttpService

interface IRandomUsersHttpService: IHttpService {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<Results>

}
