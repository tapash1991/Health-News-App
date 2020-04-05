package com.example.healthnews.Room_Database

import androidx.room.TypeConverter
import com.example.healthnews.Model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()
    private val type = object : TypeToken<Source?>() {}.type

    @TypeConverter
    fun stringToNestedData(json: String?): Source {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun nestedDataToString(source: Source?): String {
        return gson.toJson(source, type)
    }
}
