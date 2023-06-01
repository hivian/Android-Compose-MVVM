package com.hivian.compose_mvvm.data.repository

import com.hivian.compose_mvvm.domain.repository.ServiceResult
import com.hivian.compose_mvvm.data.sources.remote.ErrorType
import com.hivian.compose_mvvm.data.sources.remote.HttpResult
import com.hivian.compose_mvvm.data.mappers.ImageSize
import com.hivian.compose_mvvm.data.mappers.mapToRandomUser
import com.hivian.compose_mvvm.data.mappers.mapToRandomUsers
import com.hivian.compose_mvvm.domain.models.RandomUser
import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.domain.services.IDatabaseService
import com.hivian.compose_mvvm.domain.services.IHttpClient
import javax.inject.Inject

internal class RandomUsersRepositoryImpl @Inject constructor(
    private val database: IDatabaseService,
    private val httpClient: IHttpClient
): IRandomUsersRepository {

    override suspend fun getUserById(userId: Int, imageSize: ImageSize): RandomUser {
        return database.getUserById(userId).mapToRandomUser(imageSize)
    }

    override suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>> {
        val httpUsersResult = httpClient.fetchRandomUsers(pageIndex, pageSize)
        val databaseUserResult = fetchDatabaseUsers(pageIndex, pageSize)

        return when (httpUsersResult) {
            is HttpResult.Success -> {
                val users = httpUsersResult.data.results

                if (users.isEmpty()) {
                    ServiceResult.Error(ErrorType.NO_RESULT, databaseUserResult)
                } else {
                    database.upsertUsers(users)
                    ServiceResult.Success(fetchDatabaseUsers(pageIndex, pageSize))
                }
            }
            is HttpResult.Error -> {
                ServiceResult.Error(httpUsersResult.errorType, databaseUserResult)
            }
        }
    }

    private suspend fun fetchDatabaseUsers(pageIndex: Int, pageSize: Int): List<RandomUser> {
        return database
            .fetchUsers(pageIndex, pageSize)
            .mapToRandomUsers(ImageSize.MEDIUM)
    }

}
