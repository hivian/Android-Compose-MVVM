package com.hivian.compose_mvvm.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.data.models.Name
import com.hivian.compose_mvvm.domain.extensions.fromJson
import com.hivian.compose_mvvm.domain.extensions.toJson

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
