package com.pk.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.data.db.UnitSpecificWeatherEntry
import com.pk.weatherlogger.data.db.WeatherEntry

interface WeatherRepository {
    suspend fun getWeatherList(): LiveData<out UnitSpecificWeatherEntry>
}