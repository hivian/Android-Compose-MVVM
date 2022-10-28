package com.hivian.lydia_test.business.db

import androidx.room.TypeConverter
import com.hivian.lydia_test.business.model.dto.Location
import com.hivian.common.fromJson
import com.hivian.common.toJson

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
