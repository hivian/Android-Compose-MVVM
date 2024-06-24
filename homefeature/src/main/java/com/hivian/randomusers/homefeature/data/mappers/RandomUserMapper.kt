package com.hivian.randomusers.homefeature.data.mappers

import com.hivian.randomusers.core.data.models.RandomUserDTO
import com.hivian.randomusers.homefeature.domain.models.Address
import com.hivian.randomusers.homefeature.domain.models.RandomUser

enum class ImageSize {
    THUMBNAIL,
    MEDIUM,
    LARGE
}

fun RandomUserDTO.mapToRandomUser(imageSize: com.hivian.randomusers.homefeature.data.mappers.ImageSize) : RandomUser {
    return RandomUser(
        id = localId,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        email = email,
        phone = phone,
        cell = cell,
        picture = when (imageSize) {
            com.hivian.randomusers.homefeature.data.mappers.ImageSize.THUMBNAIL -> picture.thumbnail
            com.hivian.randomusers.homefeature.data.mappers.ImageSize.MEDIUM -> picture.medium
            com.hivian.randomusers.homefeature.data.mappers.ImageSize.LARGE -> picture.large
        },
        address = Address(
            city = location.city,
            state = location.state,
            country = location.country,
            latitude = location.coordinates.latitude,
            longitude = location.coordinates.longitude,
        )
    )
}

fun List<RandomUserDTO>.mapToRandomUsers(imageSize: com.hivian.randomusers.homefeature.data.mappers.ImageSize): List<RandomUser> {
    return map { it.mapToRandomUser(imageSize) }
}
