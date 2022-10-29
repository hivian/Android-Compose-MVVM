package com.hivian.lydia_test.core.models

import com.hivian.lydia_test.core.models.dto.RandomUserDTO
import com.hivian.lydia_test.core.models.domain.RandomUserDomain

object Mapper {

    fun mapDTOToDomain(randomUsersDTO: List<RandomUserDTO>) : List<RandomUserDomain> = randomUsersDTO.map {
        RandomUserDomain(
            id = it.localId,
            gender = it.gender,
            firstName = it.name.first,
            lastName = it.name.last,
            email = it.email,
            phone = it.phone,
            cell = it.cell,
            picture = it.picture.large)
    }

}
