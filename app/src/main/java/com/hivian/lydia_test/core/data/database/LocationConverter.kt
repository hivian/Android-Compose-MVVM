package com.hivian.lydia_test.core.data.database

import androidx.room.TypeConverter
import com.hivian.lydia_test.core.extensions.fromJson
import com.hivian.lydia_test.core.extensions.toJson
import com.hivian.lydia_test.core.models.dto.Location

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