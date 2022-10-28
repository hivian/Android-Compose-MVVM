package com.hivian.lydia_test.business.db

import androidx.room.TypeConverter
import com.hivian.common.fromJson
import com.hivian.common.toJson

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
