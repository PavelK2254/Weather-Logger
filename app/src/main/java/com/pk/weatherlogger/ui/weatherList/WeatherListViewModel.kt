package com.pk.weatherlogger.ui.weatherList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pk.weatherlogger.R
import com.pk.weatherlogger.data.network.NetworkErrorReporter
import com.pk.weatherlogger.data.provider.LocationProvider
import com.pk.weatherlogger.data.provider.UnitProvider
import com.pk.weatherlogger.data.repository.WeatherRepository
import com.pk.weatherlogger.internal.NoConnectionException
import com.pk.weatherlogger.internal.lazyDeferred
import retrofit2.HttpException
import java.lang.Exception

class WeatherListViewModel(
    private val weatherRepository: WeatherRepository,
     networkErrorReporter: NetworkErrorReporter,
    unitProvider: UnitProvider,
    locationProvider: LocationProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()
    private val location = locationProvider.getLocation()



    val weatherItem by lazyDeferred {
        weatherRepository.getWeatherList()
    }

    val weatherErrors = networkErrorReporter.getErrorReport()

    suspend fun updateWeatherValues() {
        weatherRepository.updateWeather(location,unitSystem.name)
    }

     fun wipeDatabase() {
        weatherRepository.wipeDataBase()
    }

    fun addFragment(fragment: Fragment, supportFragmentManager: FragmentManager):FragmentTransaction{
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        fragmentTransaction.replace(R.id.main_layout,fragment)
        fragmentTransaction.addToBackStack(null)
        return fragmentTransaction
    }



}