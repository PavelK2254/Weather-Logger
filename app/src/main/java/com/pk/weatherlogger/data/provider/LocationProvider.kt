package com.pk.weatherlogger.data.provider

interface LocationProvider {
    fun getLocation():String
    fun shouldUseDeviceLocation():Boolean
}