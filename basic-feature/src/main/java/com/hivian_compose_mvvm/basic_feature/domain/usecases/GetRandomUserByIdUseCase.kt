package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.datasources.ServiceResult
import com.hivian_compose_mvvm.basic_feature.data.mappers.ImageSize
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import javax.inject.Inject

class GetRandomUserByIdUseCase @Inject constructor(
    private val randomUsersService: IRandomUsersRepository
) {

    suspend operator fun invoke(userId: Int): ServiceResult<RandomUser> {
        return randomUsersService.getUserById(userId, ImageSize.LARGE)
    }

}