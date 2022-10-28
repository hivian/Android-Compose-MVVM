package com.hivian.lydia_test.business.model.dto

data class Street(

    val number: Int,

    val name: String

)

data class Location(

    val street: Street,

    val city: String,

    val state: String,

    val country: String,

    val postcode: String

)
