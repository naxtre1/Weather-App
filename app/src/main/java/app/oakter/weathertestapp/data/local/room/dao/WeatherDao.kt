package app.oakter.weathertestapp.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.oakter.weathertestapp.data.remote.beans.WeatherResponse


@Dao
public interface WeatherDao {
    @Insert
    fun insertWeather(weatherResponse: WeatherResponse?)

    @Query("DELETE FROM weather_table WHERE id = :id")
    fun deleteWeather(id: Long)

    @Query("DELETE FROM weather_table")
    fun deleteAll()

    @Query("SELECT * FROM weather_table")
    fun getWeatherData(): LiveData<WeatherResponse>?
}