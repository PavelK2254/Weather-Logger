package com.pk.weatherlogger.ui.weatherList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.recyclerview.widget.LinearLayoutManager
import com.pk.weatherlogger.R
import com.pk.weatherlogger.ui.base.ScopedActivity
import com.pk.weatherlogger.ui.settings.SettingsActivity
import com.pk.weatherlogger.ui.settings.SettingsFragment
import com.pk.weatherlogger.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class WeatherListActivity : ScopedActivity(),KodeinAware {

    override val kodein by closestKodein()
    private val weatherListVMFactory:WeatherListVMFactory by instance<WeatherListVMFactory>()

    private lateinit var viewModel: WeatherListViewModel
    private val adapter = WeatherListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@WeatherListActivity)
            adapter = this@WeatherListActivity.adapter
        }

        viewModel = ViewModelProviders.of(this,weatherListVMFactory).get(WeatherListViewModel::class.java)
        bindUI()

    }

    fun setupListeners(){
        swipeWidget.setOnRefreshListener {
            launch { viewModel.updateWeatherValues() }

        }

    }

    private fun bindUI() = launch {
        val weatherList = viewModel.weatherItem.await()
        weatherList.observe(this@WeatherListActivity, Observer { it ->
            if(it == null) return@Observer
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
}
