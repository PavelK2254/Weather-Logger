package com.pk.weatherlogger.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.pk.weatherlogger.internal.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoConnectionException()
            return chain.proceed(chain.request())
    }

    private fun isOnline():Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}