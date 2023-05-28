package com.hivian.android_mvvm_compose.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.android_mvvm_compose.core.extensions.fromJson
import com.hivian.android_mvvm_compose.core.extensions.toJson
import com.hivian.android_mvvm_compose.data.models.Location

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
