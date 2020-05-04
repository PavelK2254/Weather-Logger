package com.pk.weatherlogger.data.network

import androidx.lifecycle.LiveData
import com.pk.weatherlogger.internal.NoConnectionException
import retrofit2.HttpException
import java.lang.Exception


interface NetworkErrorReporter {
    fun addErrorReport(e:Exception)
    fun getErrorReport(): LiveData<Exception>
}