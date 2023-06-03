package com.hivian.compose_mvvm.core.datasource.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.core.datasource.models.Picture
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
