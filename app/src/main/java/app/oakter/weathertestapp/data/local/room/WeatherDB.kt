package app.oakter.weathertestapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import app.oakter.weathertestapp.data.local.room.dao.WeatherDao
import app.oakter.weathertestapp.data.remote.beans.WeatherResponse

@Database(entities = [WeatherResponse::class], version = 1, exportSchema = true)
public abstract class WeatherDB: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}