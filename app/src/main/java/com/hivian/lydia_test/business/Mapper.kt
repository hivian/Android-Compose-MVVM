package com.hivian.lydia_test.business

import com.hivian.lydia_test.business.model.dto.RandomUserDTO
import com.hivian.lydia_test.business.model.domain.RandomUserDomain

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
