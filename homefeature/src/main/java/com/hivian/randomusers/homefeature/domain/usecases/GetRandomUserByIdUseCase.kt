package com.hivian.randomusers.homefeature.domain.usecases

import com.hivian.randomusers.core.data.ServiceResult
import com.hivian.randomusers.homefeature.domain.models.RandomUser
import com.hivian.randomusers.homefeature.domain.services.IRandomUsersService

class GetRandomUserByIdUseCase(
    private val randomUsersService: IRandomUsersService
) {

    suspend operator fun invoke(userId: Int): ServiceResult<RandomUser> {
        return randomUsersService.getUserById(userId, com.hivian.randomusers.homefeature.data.mappers.ImageSize.LARGE)
    }

}
