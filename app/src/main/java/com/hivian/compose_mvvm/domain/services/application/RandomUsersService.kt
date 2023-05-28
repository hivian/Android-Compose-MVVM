package com.hivian.compose_mvvm.domain.services.application

import com.hivian.compose_mvvm.core.data.ServiceResult
import com.hivian.compose_mvvm.core.data.network.ErrorType
import com.hivian.compose_mvvm.core.data.network.HttpResult
import com.hivian.compose_mvvm.data.models.RandomUserDTO
import com.hivian.compose_mvvm.domain.mappers.ImageSize
import com.hivian.compose_mvvm.domain.mappers.mapToRandomUser
import com.hivian.compose_mvvm.domain.models.RandomUser
import com.hivian.compose_mvvm.domain.services.database.IDatabaseService
import com.hivian.compose_mvvm.domain.services.networking.IHttpClient
import javax.inject.Inject

internal class RandomUsersService @Inject constructor(
    private val database: IDatabaseService,
    private val httpClient: IHttpClient
): IRandomUsersService {

    override suspend fun getUserById(userId: Int, imageSize: ImageSize): RandomUser {
        return database.getUserById(userId).mapToRandomUser(imageSize)
    }

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
