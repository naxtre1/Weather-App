package app.oakter.weathertestapp

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import java.util.*

import javax.inject.Inject


class Repository @Inject constructor(val weatherDao: WeatherDao, val apiService: ApiService) {

    fun getWeather(lat: Double, long: Double, apiKey : String): Observable<WeatherResponse> {
        return apiService.getWeather(lat,long,"minutely,daily,alerts", "imperial" ,apiKey)
    }

    fun insertWeather(weatherResponse: WeatherResponse?) {
        weatherDao.insertWeather(weatherResponse)
    }

    fun deletePokemon(pokemonLong: Long) {
        weatherDao.deleteWeather(pokemonLong)
    }

    fun deleteAll() {
        weatherDao.deleteAll()
    }

    val getWeather: LiveData<WeatherResponse>?
        get() = weatherDao.getWeatherData()
}