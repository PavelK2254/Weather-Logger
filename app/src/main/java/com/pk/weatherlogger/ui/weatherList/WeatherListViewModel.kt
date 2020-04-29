package com.pk.weatherlogger.ui.weatherList

import androidx.lifecycle.ViewModel
import com.pk.weatherlogger.data.provider.LocationProvider
import com.pk.weatherlogger.data.provider.UnitProvider
import com.pk.weatherlogger.data.repository.WeatherRepository
import com.pk.weatherlogger.internal.lazyDeferred

class WeatherListViewModel(
    private val weatherRepository: WeatherRepository,
    unitProvider: UnitProvider,
    locationProvider: LocationProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()
    private val location = locationProvider.getLocation()


    val weatherItem by lazyDeferred {
        weatherRepository.getWeatherList()
    }

    suspend fun updateWeatherValues() {
        weatherRepository.updateWeather(location,unitSystem.name)
    }

     fun wipeDatabase() {
        weatherRepository.wipeDataBase()
    }


}