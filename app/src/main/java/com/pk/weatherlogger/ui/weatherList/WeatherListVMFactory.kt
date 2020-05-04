package com.pk.weatherlogger.ui.weatherList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pk.weatherlogger.data.network.NetworkErrorReporter
import com.pk.weatherlogger.data.provider.LocationProvider
import com.pk.weatherlogger.data.provider.UnitProvider
import com.pk.weatherlogger.data.repository.WeatherRepository

class WeatherListVMFactory(
    private val WeatherRepository: WeatherRepository,
    private val unitProvider: UnitProvider,
    private val locationProvider: LocationProvider,
    private val networkErrorReporter: NetworkErrorReporter
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherListViewModel(
            weatherRepository = WeatherRepository,
            unitProvider = unitProvider,
            locationProvider = locationProvider,
            networkErrorReporter = networkErrorReporter
        ) as T
    }
}