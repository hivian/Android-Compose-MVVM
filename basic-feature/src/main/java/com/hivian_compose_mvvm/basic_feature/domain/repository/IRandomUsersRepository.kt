package com.hivian_compose_mvvm.basic_feature.domain.repository

import com.hivian.compose_mvvm.core.datasources.ServiceResult
import com.hivian_compose_mvvm.basic_feature.data.mappers.ImageSize
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser

interface IRandomUsersRepository {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): ServiceResult<RandomUser>

}
