package com.pk.weatherlogger.data.db

import com.pk.weatherlogger.data.db.entity.WeatherX

interface UnitSpecificWeatherEntry {
    val cityName: String
    val temperature: Double
    val feelsLike: Double
    val country: String
    val currentDate: String
    val unit:String
    val weatherX : List<WeatherX>

}