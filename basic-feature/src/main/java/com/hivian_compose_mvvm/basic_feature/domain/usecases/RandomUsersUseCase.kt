package com.hivian_compose_mvvm.basic_feature.domain.usecases

import com.hivian.compose_mvvm.core.datasource.ServiceResult
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser
import com.hivian_compose_mvvm.basic_feature.domain.repository.IRandomUsersRepository
import javax.inject.Inject

class GetRandomUsersUseCase @Inject constructor(
    private val randomUsersService: IRandomUsersRepository
) {

    suspend operator fun invoke(pageIndex: Int, pageSize: Int): ServiceResult<List<RandomUser>> {
        return randomUsersService.fetchRandomUsers(pageIndex, pageSize)
    }

}