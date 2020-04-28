package com.pk.weatherlogger.ui.weatherList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pk.weatherlogger.data.repository.WeatherRepository

class WeatherListVMFactory(
    private val WeatherRepository:WeatherRepository
):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherListViewModel(weatherRepository = WeatherRepository) as T
    }
}