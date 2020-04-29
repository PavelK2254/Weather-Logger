package com.pk.weatherlogger.data.provider

import com.pk.weatherlogger.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem():UnitSystem
}