package com.hivian.lydia_test.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.lydia_test.core.extensions.fromJson
import com.hivian.lydia_test.core.extensions.toJson
import com.hivian.lydia_test.data.models.Picture

class PictureConverter {

    @TypeConverter
    fun pictureToJson(value: Picture): String {
        return value.toJson()
    }

    @TypeConverter
    fun jsonToPicture(value: String): Picture {
        return value.fromJson()
    }
}
