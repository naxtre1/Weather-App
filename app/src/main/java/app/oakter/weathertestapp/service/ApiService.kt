package app.oakter.weathertestapp.service

import app.oakter.weathertestapp.data.remote.beans.WeatherResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

public interface ApiService {
    @GET("onecall")
    fun getWeather(@Query("lat") uid: Double,
                   @Query("lon") latitude: Double,
                   @Query("exclude") exclude: String? = "minutely,daily,alerts",
                   @Query("units") units: String? = "imperial",
                   @Query("appid") longitude: String) : Observable<WeatherResponse>
}