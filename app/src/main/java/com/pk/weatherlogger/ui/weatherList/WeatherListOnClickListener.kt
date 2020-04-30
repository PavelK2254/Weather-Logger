package com.pk.weatherlogger.ui.weatherList

import com.pk.weatherlogger.data.db.WeatherEntry

interface WeatherListOnClickListener {
    fun onItemClick(weatherData:WeatherEntry)
}