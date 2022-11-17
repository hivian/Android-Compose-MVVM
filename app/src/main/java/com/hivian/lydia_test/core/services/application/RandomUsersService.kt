package com.hivian.lydia_test.core.services.application

import com.hivian.lydia_test.core.data.ErrorType
import com.hivian.lydia_test.core.data.ServiceResult
import com.hivian.lydia_test.core.data.remote.HttpResult
import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.database.IDatabaseService
import com.hivian.lydia_test.core.services.networking.IHttpClient
import javax.inject.Inject

class RandomUsersService @Inject constructor(
    private val database: IDatabaseService,
    private val httpClient: IHttpClient
): IRandomUsersService {

    override suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUserDTO>> {
        val httpUsersResult = httpClient.fetchRandomUsers(pageIndex, pageSize)
        val databaseUserResult = database.fetchUsers(pageIndex, pageSize)

        return when (httpUsersResult) {
            is HttpResult.Success -> {
                val users = httpUsersResult.data.results

                if (users.isEmpty()) {
                    ServiceResult.Error(ErrorType.NO_RESULT, databaseUserResult)
                } else {
                    database.upsertUsers(users)
                    ServiceResult.Success(database.fetchUsers(pageIndex, pageSize))
                }
            }
            is HttpResult.Error -> {
                ServiceResult.Error(httpUsersResult.errorType, databaseUserResult)
            }
        }
    }

}
