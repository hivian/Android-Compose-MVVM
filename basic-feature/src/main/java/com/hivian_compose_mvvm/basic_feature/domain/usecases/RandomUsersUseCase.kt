package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.datasources.ServiceResult
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository

class GetRandomUsersUseCase(
    private val randomUsersService: IRandomUsersRepository
) {

    suspend operator fun invoke(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>> {
        return randomUsersService.fetchRandomUsers(pageIndex, pageSize)
    }

}