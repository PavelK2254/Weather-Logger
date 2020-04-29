package com.pk.weatherlogger.ui.weatherList

import androidx.lifecycle.ViewModel
import com.pk.weatherlogger.data.repository.WeatherRepository
import com.pk.weatherlogger.internal.lazyDeferred

class WeatherListViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    val weatherItem by lazyDeferred {
        weatherRepository.getWeatherList()
    }

    suspend fun updateWeatherValues() {
        weatherRepository.updateWeather()
    }

}