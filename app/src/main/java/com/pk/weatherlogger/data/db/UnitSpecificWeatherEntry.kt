package com.pk.weatherlogger.data.db

interface UnitSpecificWeatherEntry {
    val cityName: String
    val temperature: Double
    val feelsLike: Double
    val country: String

}