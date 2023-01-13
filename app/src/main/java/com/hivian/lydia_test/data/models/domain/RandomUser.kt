package com.hivian.lydia_test.data.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomUser(

    val id: Int,

    val gender: String,

    val firstName: String,

    val lastName: String,

    val email: String,

    val phone: String,

    val cell: String,

    val picture: String

): Parcelable {

    val fullName : String
        get() = "$firstName $lastName"

}
