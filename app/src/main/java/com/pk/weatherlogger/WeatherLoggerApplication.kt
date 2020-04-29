package com.pk.weatherlogger

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pk.weatherlogger.data.db.WeatherLoggerDatabase
import com.pk.weatherlogger.data.network.*
import com.pk.weatherlogger.data.provider.LocationProvider
import com.pk.weatherlogger.data.provider.LocationProviderImpl
import com.pk.weatherlogger.data.provider.UnitProvider
import com.pk.weatherlogger.data.provider.UnitProviderImpl
import com.pk.weatherlogger.data.repository.WeatherRepository
import com.pk.weatherlogger.data.repository.WeatherRepositoryImpl
import com.pk.weatherlogger.ui.weatherList.WeatherListVMFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
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
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance()) }
        bind() from provider { WeatherListVMFactory(instance(),instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}