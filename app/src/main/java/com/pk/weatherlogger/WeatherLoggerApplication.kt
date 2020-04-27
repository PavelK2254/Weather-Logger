package com.pk.weatherlogger

import android.app.Application
import com.pk.weatherlogger.data.db.WeatherLoggerDatabase
import com.pk.weatherlogger.data.network.*
import com.pk.weatherlogger.data.repository.WeatherRepository
import com.pk.weatherlogger.data.repository.WeatherRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class WeatherLoggerApplication() : Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherLoggerApplication))

        bind() from singleton { WeatherLoggerDatabase(instance()) }
        bind() from singleton { instance<WeatherLoggerDatabase>().iWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherMapApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance())}
        bind<WeatherRepository>() with singleton { WeatherRepositoryImpl(instance(),instance())}
    }
}