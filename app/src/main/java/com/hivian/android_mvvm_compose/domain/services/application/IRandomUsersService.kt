package com.hivian.android_mvvm_compose.domain.services.application

import com.hivian.android_mvvm_compose.core.data.ServiceResult
import com.hivian.android_mvvm_compose.data.models.RandomUserDTO
import com.hivian.android_mvvm_compose.domain.mappers.ImageSize
import com.hivian.android_mvvm_compose.domain.models.RandomUser

interface IRandomUsersService {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUserDTO>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): RandomUser

}
