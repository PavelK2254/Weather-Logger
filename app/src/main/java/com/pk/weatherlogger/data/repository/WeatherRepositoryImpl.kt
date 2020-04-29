package com.pk.weatherlogger.data.repository

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.data.db.WeatherDao
import com.pk.weatherlogger.data.db.WeatherEntry
import com.pk.weatherlogger.data.db.entity.Weather
import com.pk.weatherlogger.data.network.WeatherNetworkDataSource
import com.pk.weatherlogger.data.provider.UnitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val openWeatherMapDataSource : WeatherNetworkDataSource
) : WeatherRepository {

    private lateinit var currentUnit:String

    override suspend fun getWeatherList(): LiveData<out List<WeatherEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherDao.getFullWeatherList()
        }
    }

    override suspend fun updateWeather(location: String, unitSystem: String) {
            currentUnit = unitSystem
            fetchCurrentWeather(location,currentUnit)
    }

    init {
        openWeatherMapDataSource.downloadedWeather.observeForever { newWeatherList ->
            persistFetchedWeatherList(newWeatherList)
        }

    }

    private fun persistFetchedWeatherList(fetchedWeather:Weather){
        GlobalScope.launch(Dispatchers.IO) {
            fetchedWeather.setDateAndUnit(currentUnit)
            weatherDao.upsert(fetchedWeather)
        }
    }

    private suspend fun fetchCurrentWeather(location:String,unit:String){
        openWeatherMapDataSource.fetchWeatherData(location,unit)
    }

    override fun wipeDataBase(){
        GlobalScope.launch(Dispatchers.IO){
            weatherDao.wipeWeatherLogs()
        }

    }
}