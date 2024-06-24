package com.hivian.randomusers.core.data.local.converters

import androidx.room.TypeConverter
import com.hivian.randomusers.core.data.models.Picture
import com.hivian.randomusers.core.domain.extensions.fromJson
import com.hivian.randomusers.core.domain.extensions.toJson

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
