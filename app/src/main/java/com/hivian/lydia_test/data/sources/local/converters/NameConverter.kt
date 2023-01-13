package com.hivian.lydia_test.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.lydia_test.core.extensions.fromJson
import com.hivian.lydia_test.core.extensions.toJson
import com.hivian.lydia_test.data.models.dto.Name

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
