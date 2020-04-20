package com.pk.weatherlogger.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pk.weatherlogger.data.db.entity.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntity: Weather)

    @Query("select * from weather")
    fun getFullWeatherList(): LiveData<Weather>
}