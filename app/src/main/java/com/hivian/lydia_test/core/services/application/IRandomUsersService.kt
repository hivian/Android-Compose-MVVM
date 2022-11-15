package com.hivian.lydia_test.core.services.application

import com.hivian.lydia_test.core.base.data.ServiceResult
import com.hivian.lydia_test.core.models.dto.RandomUserDTO

interface IRandomUsersService {

    suspend fun fetchRandomUsers(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUserDTO>>

}
