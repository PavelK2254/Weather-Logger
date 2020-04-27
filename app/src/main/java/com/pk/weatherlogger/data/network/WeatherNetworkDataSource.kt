package com.pk.weatherlogger.data.network

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.data.db.entity.Weather

interface WeatherNetworkDataSource {
    val downloadedWeather: LiveData<Weather>
    suspend fun fetchWeatherData(
        location: String,
        temperatureUnits: String
    )
}