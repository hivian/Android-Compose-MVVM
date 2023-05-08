package com.hivian.lydia_test.domain.services.application

import com.hivian.lydia_test.core.data.ServiceResult
import com.hivian.lydia_test.domain.mappers.ImageSize
import com.hivian.lydia_test.domain.models.RandomUser
import com.hivian.lydia_test.data.models.RandomUserDTO

interface IRandomUsersService {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUserDTO>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): RandomUser

}
