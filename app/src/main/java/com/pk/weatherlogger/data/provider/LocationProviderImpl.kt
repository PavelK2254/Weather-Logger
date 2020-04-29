package com.pk.weatherlogger.data.provider

import android.content.Context

const val LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(context: Context) : BaseProvider(context),LocationProvider {

    override fun getLocation(): String {
        val selectedName = preferences.getString(LOCATION,"London")
        return selectedName!!
    }
}