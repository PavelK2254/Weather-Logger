package com.pk.weatherlogger.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
   // val base: String,
   // val clouds: Clouds,
  //  val cod: Int,
  //  val coord: Coord,
   // val dt: Int,
   // val id: Int,
   // val timezone: Int,
   // val visibility: Int,
   // val weather: List<WeatherX>,
   // val wind: Wind,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}