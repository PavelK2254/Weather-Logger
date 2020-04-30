package com.pk.weatherlogger.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pk.weatherlogger.R
import kotlinx.android.synthetic.main.detail_screen.view.*

class DetailFragment: Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        val rootView = inflater.inflate(R.layout.detail_screen,container,false)
        val currentUnit:String = if(arguments?.getString("unit") == "IMPERIAL") "°F" else "°C"
        rootView.detail_cityname.text = "${arguments?.getString("cityName")}, ${arguments?.getString("country")}"
        rootView.detail_date.text = arguments?.getString("currentDate")
        rootView.detail_temp.text = "${arguments?.getDouble("temperature")} $currentUnit"
        rootView.detail_feels_like.text = "Feels Like: ${arguments?.getDouble("feelsLike")}"
        return rootView
    }
}