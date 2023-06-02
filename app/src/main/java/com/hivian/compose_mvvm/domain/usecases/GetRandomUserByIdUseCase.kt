package com.hivian.compose_mvvm.domain.usecases

import com.hivian.compose_mvvm.data.mappers.ImageSize
import com.hivian.compose_mvvm.domain.models.RandomUser
import com.hivian.compose_mvvm.domain.repository.IRandomUsersRepository
import com.hivian.compose_mvvm.domain.repository.ServiceResult
import javax.inject.Inject

class GetRandomUserByIdUseCase @Inject constructor(
    private val randomUsersService: IRandomUsersRepository
) {

    suspend operator fun invoke(userId: Int): ServiceResult<RandomUser> {
        return randomUsersService.getUserById(userId, ImageSize.LARGE)
    }

}