package com.hivian.compose_mvvm.data.mappers

import com.hivian.compose_mvvm.data.sources.models.RandomUserDTO
import com.hivian.compose_mvvm.domain.models.RandomUser

enum class ImageSize {
    THUMBNAIL,
    MEDIUM,
    LARGE
}

fun RandomUserDTO.mapToRandomUser(imageSize: ImageSize) : RandomUser {
    return RandomUser(
        id = localId,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        email = email,
        phone = phone,
        cell = cell,
        picture = when (imageSize) {
            ImageSize.THUMBNAIL -> picture.thumbnail
            ImageSize.MEDIUM -> picture.medium
            ImageSize.LARGE -> picture.large
        })
}

fun List<RandomUserDTO>.mapToRandomUsers(imageSize: ImageSize): List<RandomUser> {
    return map { it.mapToRandomUser(imageSize) }
}
