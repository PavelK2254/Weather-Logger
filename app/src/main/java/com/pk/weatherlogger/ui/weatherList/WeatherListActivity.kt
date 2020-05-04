@file:Suppress("DEPRECATION")

package com.pk.weatherlogger.ui.weatherList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pk.weatherlogger.R
import com.pk.weatherlogger.data.db.WeatherEntry
import com.pk.weatherlogger.data.db.entity.WeatherX
import com.pk.weatherlogger.internal.NoConnectionException
import com.pk.weatherlogger.ui.base.ScopedActivity
import com.pk.weatherlogger.ui.detail.DetailFragment
import com.pk.weatherlogger.ui.settings.SettingsActivity
import com.pk.weatherlogger.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import retrofit2.HttpException
import java.net.ConnectException


class WeatherListActivity : ScopedActivity(),WeatherListOnClickListener,KodeinAware {

    override val kodein by closestKodein()
    private val weatherListVMFactory:WeatherListVMFactory by instance<WeatherListVMFactory>()

    private lateinit var viewModel: WeatherListViewModel
    private val adapter = WeatherListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@WeatherListActivity)
            adapter = this@WeatherListActivity.adapter

        }

        viewModel = ViewModelProviders.of(this,weatherListVMFactory).get(WeatherListViewModel::class.java)
        setupListeners()
        bindUI()

    }

    private fun setupListeners(){
        swipeWidget.setOnRefreshListener {
            launch { viewModel.updateWeatherValues() }

        }

        viewModel.weatherErrors.observe(this, Observer {
            swipeWidget.isRefreshing = false
            when(it){
                is NoConnectionException -> Toast.makeText(this,getString(R.string.noInternetConnection),Toast.LENGTH_SHORT).show()
                is HttpException ->  Toast.makeText(this,getString(R.string.invalidLocation),Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun bindUI() = launch {
        val weatherList = viewModel.weatherItem.await()
        weatherList.observe(this@WeatherListActivity, Observer { it ->
            if(it == null) {
                tv.visibility = View.VISIBLE
                return@Observer
            }else{
                tv.visibility = View.GONE
            }
            it.forEach {
                adapter.addToDataSet(it)
            }
            swipeWidget.isRefreshing = false
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_refresh -> {
            launch {
                swipeWidget.isRefreshing = true
                viewModel.updateWeatherValues()
            }
            true
        }

        R.id.menu_settings -> {
            val intent = Intent(this,SettingsActivity::class.java)
            //startActivity(intent)
            startActivityForResult(intent,Constants.WIPE_DB_CODE)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.WIPE_DB_CODE) {
            if (resultCode == Activity.RESULT_OK){
                if (data != null) {
                    if(data.hasExtra("wipeDB") && data.getBooleanExtra("wipeDB",false)){
                        viewModel.wipeDatabase()
                        adapter.clearAdapter()
                    }

                }
            }
        }
    }

    override fun onItemClick(weatherData: WeatherEntry) {
        Log.i("FragmentClicked",weatherData.toString())
        val detailFragment = DetailFragment()
        val weatherCondition:WeatherX = weatherData.weatherX[0]
        val bundle = Bundle().also {
            it.putString("cityName",weatherData.cityName)
            it.putString("country",weatherData.country)
            it.putString("currentDate",weatherData.currentDate)
            it.putString("unit",weatherData.unit)
            it.putDouble("temperature",weatherData.temperature)
            it.putDouble("feelsLike",weatherData.feelsLike)
            it.putString("condition_main",weatherCondition.main)
            it.putString("condition_description",weatherCondition.description)
            it.putString("condition_icon",weatherCondition.icon)
        }
        detailFragment.arguments = bundle
        viewModel.addFragment(detailFragment,supportFragmentManager).commit()
    }



    override fun onResume() {
        super.onResume()
        if (recycler_view.layoutManager?.itemCount!! < 1){
            tv.visibility = View.VISIBLE
        }
    }
}
