package com.pk.weatherlogger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pk.weatherlogger.R
import com.pk.weatherlogger.data.network.OpenWeatherMapApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        val apiService =
            OpenWeatherMapApiService()

    }
}
