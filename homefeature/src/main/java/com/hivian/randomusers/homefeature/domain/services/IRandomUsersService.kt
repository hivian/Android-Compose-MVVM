package com.hivian.randomusers.homefeature.domain.services

import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.homefeature.data.mappers.ImageSize
import com.hivian.randomusers.homefeature.domain.models.RandomUser

interface IRandomUsersService {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): ServiceResult<RandomUser>

}
