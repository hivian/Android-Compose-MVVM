package com.hivian.randomusers.core.data.models

data class Picture(

    val large: String,

    val medium: String,

    val thumbnail: String

) {

    companion object {
        val EMPTY = Picture(large = "", medium = "", thumbnail = "")
    }

}
