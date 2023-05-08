package com.hivian.lydia_test.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.lydia_test.core.extensions.fromJson
import com.hivian.lydia_test.core.extensions.toJson
import com.hivian.lydia_test.data.models.Location

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
