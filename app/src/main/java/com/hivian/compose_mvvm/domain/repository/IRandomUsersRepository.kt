package com.hivian.compose_mvvm.domain.repository

import com.hivian.compose_mvvm.data.mappers.ImageSize
import com.hivian.compose_mvvm.domain.models.RandomUser

interface IRandomUsersRepository {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): ServiceResult<RandomUser>

}
