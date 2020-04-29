package com.pk.weatherlogger.data.db

import androidx.room.ColumnInfo

data class WeatherEntry(
    @ColumnInfo(name = "name")
    override val cityName: String,
    @ColumnInfo(name = "main_temp")
    override val temperature: Double,
    @ColumnInfo(name = "main_feelsLike")
    override val feelsLike: Double,
    @ColumnInfo(name = "sys_country")
    override val country: String,

    @ColumnInfo(name = "currentDate")
    override val currentDate: String,

    @ColumnInfo(name = "unit")
    override val unit: String


):UnitSpecificWeatherEntry