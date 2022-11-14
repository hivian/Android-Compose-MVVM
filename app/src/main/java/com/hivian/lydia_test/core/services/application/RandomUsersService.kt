package com.hivian.lydia_test.core.services.application

import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.remote.ResourceErrorType
import com.hivian.lydia_test.core.remote.ServiceResult
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.networking.IHttpClient
import javax.inject.Inject

class RandomUsersService @Inject constructor(
    private val database: IDatabaseService,
    private val httpClient: IHttpClient
): IRandomUsersService {

    override suspend fun fetchRandomUsers(page: Int, results: Int): ServiceResult<List<RandomUserDTO>> {
        val httpResult = httpClient.fetchRandomUsers(page, results)

        if (httpResult is ServiceResult.Success) {
            database.upsertUsers(httpResult.data.results)
        }

        val databaseUsers = database.fetchUsers().ifEmpty {
            return ServiceResult.Error(ResourceErrorType.NO_RESULT)
        }

        return ServiceResult.Success(databaseUsers)
    }

}
