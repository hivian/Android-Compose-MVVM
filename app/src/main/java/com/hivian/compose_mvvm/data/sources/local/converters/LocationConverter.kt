package com.hivian.compose_mvvm.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.core.extensions.fromJson
import com.hivian.compose_mvvm.core.extensions.toJson
import com.hivian.compose_mvvm.data.models.Location

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
