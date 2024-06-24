package com.hivian.randomusers.homefeature.domain.usecases

import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.homefeature.domain.models.RandomUser
import com.hivian.randomusers.homefeature.domain.services.IRandomUsersService

class GetRandomUsersUseCase(
    private val randomUsersService: IRandomUsersService
) {

    suspend operator fun invoke(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>> {
        return randomUsersService.fetchRandomUsers(pageIndex, pageSize)
    }

}