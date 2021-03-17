package app.oakter.weathertestapp.data

import androidx.lifecycle.LiveData
import app.oakter.weathertestapp.data.local.room.dao.WeatherDao
import app.oakter.weathertestapp.data.remote.beans.WeatherResponse
import app.oakter.weathertestapp.service.ApiService
import io.reactivex.rxjava3.core.Observable

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