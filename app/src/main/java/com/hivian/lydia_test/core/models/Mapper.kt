package com.hivian.lydia_test.core.models

import com.hivian.lydia_test.core.models.domain.RandomUserDomain
import com.hivian.lydia_test.core.models.dto.RandomUserDTO

enum class ImageSize {
    THUMBNAIL,
    MEDIUM,
    LARGE
}

object Mapper {

    fun mapDTOToDomain(randomUsersDTO: List<RandomUserDTO>, imageSize: ImageSize) : List<RandomUserDomain> = randomUsersDTO.map {
        mapDTOToDomain(it, imageSize)
    }

    fun mapDTOToDomain(randomUsersDTO: RandomUserDTO, imageSize: ImageSize) : RandomUserDomain {
        return RandomUserDomain(
            id = randomUsersDTO.localId,
            gender = randomUsersDTO.gender,
            firstName = randomUsersDTO.name.first,
            lastName = randomUsersDTO.name.last,
            email = randomUsersDTO.email,
            phone = randomUsersDTO.phone,
            cell = randomUsersDTO.cell,
            picture = when (imageSize) {
                ImageSize.THUMBNAIL -> randomUsersDTO.picture.thumbnail
                ImageSize.MEDIUM -> randomUsersDTO.picture.medium
                ImageSize.LARGE -> randomUsersDTO.picture.large
            })
    }

}
