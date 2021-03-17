package app.oakter.weathertestapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.oakter.weathertestapp.data.Repository
import app.oakter.weathertestapp.data.remote.beans.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(val repository: Repository): ViewModel() {
    private val TAG = "WeatherViewModel"
    private val wList: MutableLiveData<WeatherResponse> =
        MutableLiveData<WeatherResponse>()
    private var weatherList: LiveData<WeatherResponse>? = null

    init {
        weatherList = repository.getWeather
    }

    fun getWeatherData(lat: Double, long: Double, apiKey: String) {
        repository?.getWeather(lat, long, apiKey)
            ?.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                run {
                    Log.e("TAG", TAG +  result.toString())
                    wList?.value = result
                }
            }
            ) { error -> Log.e(TAG, "weather: " + error.message) }
    }

    fun insertWeather(weatherResponse: WeatherResponse?) {
        repository.insertWeather(weatherResponse)
    }

    fun deleteWeather(name: Long) {
        repository!!.deletePokemon(name)
    }

    fun getWeatherList(): LiveData<WeatherResponse>? {
        return wList
    }

    fun getWeather() {
        weatherList = repository!!.getWeather
    }


}