package com.pk.weatherlogger.ui.weatherList

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pk.weatherlogger.R
import com.pk.weatherlogger.ui.base.ScopedActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.ZonedDateTime


class WeatherListActivity : ScopedActivity(),KodeinAware {

    override val kodein by closestKodein()
    private val weatherListVMFactory:WeatherListVMFactory by instance<WeatherListVMFactory>()

    private lateinit var viewModel: WeatherListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this,weatherListVMFactory).get(WeatherListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val weatherList = viewModel.weatherItem.await()
        weatherList.observe(this@WeatherListActivity, Observer {
            if(it == null) return@Observer

            tv.text = it.toString()
        })
    }

    override fun onResume() {
        super.onResume()
      /*  val apiService =
            OpenWeatherMapApiService(ConnectivityInterceptorImpl(this!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
        weatherNetworkDataSource.downloadedWeather.observe(this, Observer {
            tv.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main){
            weatherNetworkDataSource.fetchWeatherData("riga","metric")
        }*/

       // tv.text = ZonedDateTime.now().toString().split("T")[0]
    }


}
