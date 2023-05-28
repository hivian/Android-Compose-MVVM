package com.hivian.compose_mvvm.domain.services.application

import com.hivian.compose_mvvm.core.data.ServiceResult
import com.hivian.compose_mvvm.data.models.RandomUserDTO
import com.hivian.compose_mvvm.domain.mappers.ImageSize
import com.hivian.compose_mvvm.domain.models.RandomUser

interface IRandomUsersService {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUserDTO>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): RandomUser

}
