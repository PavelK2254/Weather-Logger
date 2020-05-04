package com.pk.weatherlogger.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pk.weatherlogger.data.db.entity.Weather
import com.pk.weatherlogger.internal.NoConnectionException
import retrofit2.HttpException

class WeatherNetworkDataSourceImpl(
    private val openWeatherAPI: OpenWeatherMapApiService,
    private val networkErrorReporter: NetworkErrorReporter
) : WeatherNetworkDataSource {
    private val _downloadedWeather = MutableLiveData<Weather>()
    override val downloadedWeather: LiveData<Weather>
        get() = _downloadedWeather

    override suspend fun fetchWeatherData(location: String, temperatureUnits: String) {
        try {
            val fetchedWeatherList = openWeatherAPI.getCurrentWeather(location, temperatureUnits)
                .await()
            _downloadedWeather.postValue(fetchedWeatherList)
        } catch (e: NoConnectionException) {
            Log.e("Connectivity", "No internet connection", e)
            networkErrorReporter.addErrorReport(e)
        } catch (e: HttpException) {
            Log.e("Http_Error", e.message())
            networkErrorReporter.addErrorReport(e)
        }
    }
}