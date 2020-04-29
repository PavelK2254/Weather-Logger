package com.pk.weatherlogger.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pk.weatherlogger.data.db.entity.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntity: Weather)

    @Query("select * from weather")
    fun getFullWeatherList(): LiveData<List<WeatherEntry>>

    @Query("delete from weather")
    fun wipeWeatherLogs()
}