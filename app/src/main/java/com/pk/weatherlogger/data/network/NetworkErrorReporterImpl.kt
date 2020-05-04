package com.pk.weatherlogger.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pk.weatherlogger.internal.NoConnectionException
import retrofit2.HttpException
import java.lang.Exception

class NetworkErrorReporterImpl : NetworkErrorReporter {

    val errorReports = MutableLiveData<Exception>()

    override fun addErrorReport(e: Exception) {
        errorReports.postValue(e)
    }

    override fun getErrorReport(): LiveData<Exception> {
        return errorReports
    }


}