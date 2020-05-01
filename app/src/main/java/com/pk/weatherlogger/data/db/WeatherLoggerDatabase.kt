package com.pk.weatherlogger.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pk.weatherlogger.data.db.entity.Weather
import java.security.AccessControlContext

const val DB_Version = 1

@Database(
    entities = [Weather::class],
    version = DB_Version
)

@TypeConverters(Converters::class)

abstract class WeatherLoggerDatabase:RoomDatabase() {
    abstract fun iWeatherDao(): WeatherDao

    companion object{
        @Volatile private var instance: WeatherLoggerDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDb(context).also { instance = it }
        }
        private fun buildDb(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherLoggerDatabase::class.java,"weatherlogger.db").fallbackToDestructiveMigration().build()


    }
}