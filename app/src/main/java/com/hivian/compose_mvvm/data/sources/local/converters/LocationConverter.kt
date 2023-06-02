package com.hivian.compose_mvvm.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.data.sources.models.Location
import com.hivian.compose_mvvm.domain.extensions.fromJson
import com.hivian.compose_mvvm.domain.extensions.toJson

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
