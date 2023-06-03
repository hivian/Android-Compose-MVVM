package com.hivian.compose_mvvm.core.datasource.models

data class Picture(

    val large: String,

    val medium: String,

    val thumbnail: String

) {

    companion object {
        val EMPTY = Picture(large = "", medium = "", thumbnail = "")
    }

}
