package com.hivian.lydia_test.business.db

import androidx.room.TypeConverter
import com.hivian.lydia_test.business.model.dto.Picture
import com.hivian.common.fromJson
import com.hivian.common.toJson

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
