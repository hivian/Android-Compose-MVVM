package com.hivian.compose_mvvm.core.datasource.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.core.datasource.models.Name
import com.hivian.compose_mvvm.core.extensions.fromJson
import com.hivian.compose_mvvm.core.extensions.toJson

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
