package com.pk.weatherlogger.ui.weatherList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pk.weatherlogger.R
import com.pk.weatherlogger.data.db.WeatherEntry
import com.pk.weatherlogger.data.db.entity.Weather
import kotlinx.android.synthetic.main.weather_node.view.*

class WeatherListAdapter(
   val weatherListOnClickListener: WeatherListOnClickListener
): RecyclerView.Adapter<WeatherListAdapter.WeatherNode>() {

    private var dataSet: MutableList<WeatherEntry> = ArrayList()

    fun addToDataSet(value: WeatherEntry){
        if(!dataSet.contains(value)){
            dataSet.add(value)
            notifyItemInserted(dataSet.size)
        }
    }

    fun clearAdapter(){
        dataSet.clear()
        notifyDataSetChanged()
    }


    class WeatherNode(itemView: CardView, val weatherListOnClickListener: WeatherListOnClickListener): RecyclerView.ViewHolder(itemView){
        var cityName: TextView = itemView.city_name
        var temperature: TextView = itemView.temperature
        var date: TextView = itemView.date
        fun bindListener(weatherEntry: WeatherEntry){
            itemView.setOnClickListener {
                weatherListOnClickListener.onItemClick(weatherEntry)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListAdapter.WeatherNode {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_node,parent,false) as CardView
        return WeatherNode(cardView,weatherListOnClickListener)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: WeatherNode, position: Int) {
        val currentWeatherItem = dataSet[position]
        val currentUnit:String = if(currentWeatherItem.unit == "IMPERIAL") "°F" else "°C"
        holder.cityName.text = currentWeatherItem.cityName
        holder.temperature.text = "Temperature: ${currentWeatherItem.temperature.toString()} ${currentUnit}"
        holder.date.text = "Date: ${currentWeatherItem.currentDate}"
        holder.bindListener(dataSet[position])
    }


}