package com.pk.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.data.db.UnitSpecificWeatherEntry
import com.pk.weatherlogger.data.db.WeatherDao
import com.pk.weatherlogger.data.network.OpenWeatherMapApiService

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : WeatherRepository {
    override suspend fun getWeatherList(): LiveData<UnitSpecificWeatherEntry> {
        TODO("Not yet implemented")
    }
}