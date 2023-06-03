package com.hivian.compose_mvvm.core.datasources.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.core.datasources.models.Picture
import com.hivian.compose_mvvm.core.extensions.fromJson
import com.hivian.compose_mvvm.core.extensions.toJson

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
