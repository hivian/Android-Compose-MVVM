package com.hivian.randomusers.core.data.local.converters

import androidx.room.TypeConverter
import com.hivian.randomusers.core.data.models.Name
import com.hivian.randomusers.core.domain.extensions.fromJson
import com.hivian.randomusers.core.domain.extensions.toJson

class NameConverter {

    @TypeConverter
    fun nameToJson(value: Name): String {
        return value.toJson()
    }

    @TypeConverter
    fun jsonToName(value: String): Name {
        return value.fromJson()
    }
}
