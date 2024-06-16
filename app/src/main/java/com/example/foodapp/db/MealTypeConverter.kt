package com.example.foodapp.db

import androidx.room.TypeConverter

class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attr: Any?): String {
        if (attr == null)
            return ""
        return attr as String
    }

    @TypeConverter
    fun fromStringToAny(attr: String?): Any{
        if(attr == null)
            return ""
        return attr

    }

}