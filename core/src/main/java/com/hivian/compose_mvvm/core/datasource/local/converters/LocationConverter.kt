package com.hivian.compose_mvvm.core.datasource.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.core.datasource.models.Location
import com.hivian.compose_mvvm.core.extensions.fromJson
import com.hivian.compose_mvvm.core.extensions.toJson

class LocationConverter {

    @TypeConverter
    fun locationToJson(value: Location): String {
        return value.toJson()
    }

    @TypeConverter
    fun jsonToLocation(value: String): Location {
        return value.fromJson()
    }
}
