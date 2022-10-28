package com.hivian.lydia_test.business.db

import androidx.room.TypeConverter
import com.hivian.lydia_test.core.services.extensions.fromJson
import com.hivian.lydia_test.core.services.extensions.toJson

class StringListConverter {

    @TypeConverter
    fun listToJson(value: List<String>): String {
        return value.toJson()
    }

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        return value.fromJson()
    }
}
