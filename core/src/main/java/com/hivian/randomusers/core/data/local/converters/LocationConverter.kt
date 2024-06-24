package com.hivian.randomusers.core.data.local.converters

import androidx.room.TypeConverter
import com.hivian.randomusers.core.data.models.Location
import com.hivian.randomusers.core.domain.extensions.fromJson
import com.hivian.randomusers.core.domain.extensions.toJson

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
