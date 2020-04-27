package com.pk.weatherlogger.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pk.weatherlogger.data.db.entity.Weather
import com.pk.weatherlogger.internal.NoConnectionException

class WeatherNetworkDataSourceImpl(
    private val OpenWeatherAPI : OpenWeatherMapApiService
) : WeatherNetworkDataSource {
    private val _downloadedWeather = MutableLiveData<Weather>()
    override val downloadedWeather: LiveData<Weather>
        get() = _downloadedWeather

    override suspend fun fetchWeatherData(location: String, temperatureUnits: String) {
        try {
            val fetchedWeatherList = OpenWeatherAPI.getCurrentWeather(location,temperatureUnits)
                .await()
            _downloadedWeather.postValue(fetchedWeatherList)
        }
        catch (e:NoConnectionException){
            Log.e("Connectivity","No internet connection",e)
        }
    }
}