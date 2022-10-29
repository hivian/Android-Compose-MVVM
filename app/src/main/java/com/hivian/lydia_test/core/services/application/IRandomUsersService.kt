package com.hivian.lydia_test.core.services.application

import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.services.networking.HttpResult

interface IRandomUsersService {

    suspend fun fetchRandomUsers(page: Int, results: Int): HttpResult<List<RandomUserDTO>>

}
