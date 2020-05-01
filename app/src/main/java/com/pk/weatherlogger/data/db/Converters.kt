package com.pk.weatherlogger.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pk.weatherlogger.data.db.entity.WeatherX

class Converters {

    @TypeConverter
    fun listToJson(value: List<WeatherX>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<WeatherX>::class.java).toList()

}