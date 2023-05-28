package com.hivian.android_mvvm_compose.data.sources.local.converters

import androidx.room.TypeConverter
import com.hivian.android_mvvm_compose.core.extensions.fromJson
import com.hivian.android_mvvm_compose.core.extensions.toJson
import com.hivian.android_mvvm_compose.data.models.Picture

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
