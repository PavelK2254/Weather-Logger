package com.pk.weatherlogger.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pk.weatherlogger.data.db.entity.Weather
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "af606452213e2507cb88ebc8b8dded09"
/*Test Request
https://api.openweathermap.org/data/2.5/weather?q=riga&units=metric&appid=af606452213e2507cb88ebc8b8dded09
*/

interface OpenWeatherMapApiService {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("units") temperatureUnits: String = "metric"):Deferred<Weather>

    companion object{
        operator fun invoke(): OpenWeatherMapApiService {
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)
        }
    }
}