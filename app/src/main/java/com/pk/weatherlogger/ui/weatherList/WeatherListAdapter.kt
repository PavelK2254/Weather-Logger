package com.pk.weatherlogger.ui.weatherList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pk.weatherlogger.R
import com.pk.weatherlogger.data.db.WeatherEntry
import com.pk.weatherlogger.data.db.entity.Weather
import kotlinx.android.synthetic.main.weather_node.view.*

class WeatherListAdapter: RecyclerView.Adapter<WeatherListAdapter.WeatherNode>() {

    private var dataSet: MutableList<WeatherEntry> = ArrayList()

    fun addToDataSet(value: WeatherEntry){
        dataSet.add(value)
        notifyItemInserted(0)
    }


    class WeatherNode(itemView: CardView): RecyclerView.ViewHolder(itemView){
        var cityName = itemView.city_name
        var temperature = itemView.temperature
        var date = itemView.date
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListAdapter.WeatherNode {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_node,parent,false) as CardView
        return WeatherNode(cardView)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: WeatherNode, position: Int) {
        val currentWeatherItem = dataSet[position]
        holder.cityName.text = currentWeatherItem.cityName
        holder.temperature.text = "Temperature: ${currentWeatherItem.temperature.toString()}"
        holder.date.text = "Date: ${currentWeatherItem.currentDate}"
    }
}