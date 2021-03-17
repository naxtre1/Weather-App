package app.oakter.weathertestapp

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [WeatherResponse::class], version = 1, exportSchema = true)
public abstract class WeatherDB: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}