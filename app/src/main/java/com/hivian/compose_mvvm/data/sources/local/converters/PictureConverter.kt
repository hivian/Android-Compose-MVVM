package com.hivian.compose_mvvm.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.compose_mvvm.data.sources.models.Picture
import com.hivian.compose_mvvm.domain.extensions.fromJson
import com.hivian.compose_mvvm.domain.extensions.toJson

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
