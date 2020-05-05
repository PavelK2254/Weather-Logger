package com.pk.weatherlogger.ui.weatherList

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pk.weatherlogger.R
import com.pk.weatherlogger.data.network.NetworkErrorReporter
import com.pk.weatherlogger.data.provider.LocationProvider
import com.pk.weatherlogger.data.provider.UnitProvider
import com.pk.weatherlogger.data.repository.WeatherRepository
import com.pk.weatherlogger.internal.lazyDeferred
import com.pk.weatherlogger.utils.Constants.Companion.LOCATION_PERMISSION
import java.util.*

class WeatherListViewModel(
    private val weatherRepository: WeatherRepository,
    networkErrorReporter: NetworkErrorReporter,
    unitProvider: UnitProvider,
    private val locationProvider: LocationProvider,
    private val context: Context
) : ViewModel() {

    val permissionRequest = MutableLiveData<String>()
    private val unitSystem = unitProvider.getUnitSystem()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private var hasLocationPermissions: Boolean = prefs.getBoolean(LOCATION_PERMISSION, false)
    private val shouldUseDeviceLocation = locationProvider.shouldUseDeviceLocation()
    private var deviceLocation = ""

    init {
        getLocation()
    }

    @SuppressLint("ApplySharedPref")
    fun onPermissionResult(permission: String, granted: Boolean) {
        if (permission == LOCATION_PERMISSION) {
            hasLocationPermissions = granted
            prefs.edit().putBoolean(LOCATION_PERMISSION,true).commit()
        }
    }

    val weatherItem by lazyDeferred {
        weatherRepository.getWeatherList()
    }

    val weatherErrors = networkErrorReporter.getErrorReport()

    suspend fun updateWeatherValues() {
         val location =
            if (deviceLocation != "") deviceLocation else locationProvider.getLocation()
        weatherRepository.updateWeather(location, unitSystem.name)
    }

    fun wipeDatabase() {
        weatherRepository.wipeDataBase()
    }

    fun addFragment(
        fragment: Fragment,
        supportFragmentManager: FragmentManager
    ): FragmentTransaction {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        fragmentTransaction.replace(R.id.main_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        return fragmentTransaction
    }

    private fun getLocation() {
        var cityName: String = ""
        if (shouldUseDeviceLocation && hasLocationPermissions) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    try {
                        val addresses: List<Address> =
                            geocoder.getFromLocation(location!!.latitude, location.longitude, 1)
                        cityName = addresses[0].locality
                        deviceLocation = cityName
                        Log.i("LOCATION", cityName)
                    } catch (e: Exception) {
                        Log.e("LOCATION", "Failed ${e.message}")
                    }
                }.addOnFailureListener {
                    Log.e("LOCATION", "Failed ${it.message}")
                }
        } else {
            permissionRequest.postValue(LOCATION_PERMISSION)
        }

    }

}