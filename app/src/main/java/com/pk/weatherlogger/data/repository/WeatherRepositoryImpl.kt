package com.pk.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.data.db.UnitSpecificWeatherEntry
import com.pk.weatherlogger.data.db.WeatherDao
import com.pk.weatherlogger.data.db.entity.Weather
import com.pk.weatherlogger.data.network.OpenWeatherMapApiService
import com.pk.weatherlogger.data.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val openWeatherMapDataSource : WeatherNetworkDataSource
) : WeatherRepository {
    override suspend fun getWeatherList(): LiveData<out UnitSpecificWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherDao.getFullWeatherList()
        }
    }

    init {
        openWeatherMapDataSource.downloadedWeather.observeForever { newWeatherList ->
            persistFetchedWeatherList(newWeatherList)
        }
    }

    private fun persistFetchedWeatherList(fetchedWeather:Weather){
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsert(fetchedWeather)
        }
    }

    private suspend fun fetchCurrentWeather(){
        openWeatherMapDataSource.fetchWeatherData("riga","metric")
    }


}