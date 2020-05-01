package com.pk.weatherlogger.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime
import java.util.*

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
    @SerializedName("weather")
    val weatherx: List<WeatherX>,
   // val wind: Wind,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "sys_")
    val sys: Sys,

    var currentDate:String,
    var unit:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    fun setDateAndUnit(currentUnit: String){
        currentDate = ZonedDateTime.now().toString().split("T")[0]
        unit = currentUnit
    }


}