package com.hivian.randomusers.core.data.models

data class Street(

    val number: Int = 0,

    val name: String = ""

) {
    companion object {
        val EMPTY = Street(0, "")
    }

}

data class Coordinates(

    val latitude: Double,

    val longitude: Double

) {

    companion object {
        val EMPTY = Coordinates(0.0, 0.0)
    }

}

data class Location(

    val street: Street,

    val city: String,

    val state: String,

    val country: String,

    val postcode: String,

    val coordinates: Coordinates

) {

    companion object {
        val EMPTY = Location(
            street = Street.EMPTY, city = "", state = "", country = "", postcode = "", Coordinates.EMPTY
        )
    }

}
