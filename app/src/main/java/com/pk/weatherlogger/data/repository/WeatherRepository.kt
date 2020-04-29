package com.pk.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.data.db.UnitSpecificWeatherEntry
import com.pk.weatherlogger.data.db.WeatherEntry
import com.pk.weatherlogger.data.provider.UnitProvider
import com.pk.weatherlogger.internal.UnitSystem

interface WeatherRepository {
    suspend fun getWeatherList(): LiveData<out List<WeatherEntry>>
    suspend fun updateWeather(location:String,unitSystem: String)
    fun wipeDataBase()
}