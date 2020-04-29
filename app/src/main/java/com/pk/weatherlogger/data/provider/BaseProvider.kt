package com.pk.weatherlogger.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

open class BaseProvider(context: Context) {
    private val appContext = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}