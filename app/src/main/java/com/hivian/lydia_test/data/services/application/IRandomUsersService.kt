package com.hivian.lydia_test.data.services.application

import com.hivian.lydia_test.core.data.ServiceResult
import com.hivian.lydia_test.data.mappers.ImageSize
import com.hivian.lydia_test.data.models.domain.RandomUser
import com.hivian.lydia_test.data.models.dto.RandomUserDTO

interface IRandomUsersService {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUserDTO>>

    suspend fun getUserById(userId: Int, imageSize: ImageSize): RandomUser

}
