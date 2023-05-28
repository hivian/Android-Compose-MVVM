package com.hivian.android_mvvm_compose.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.android_mvvm_compose.core.extensions.fromJson
import com.hivian.android_mvvm_compose.core.extensions.toJson
import com.hivian.android_mvvm_compose.data.models.Name

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
