package app.oakter.weathertestapp.data.local.room.converters

import androidx.room.TypeConverter
import app.oakter.weathertestapp.data.remote.beans.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherListConverter {
    private val gSon = Gson()

    @TypeConverter
    public fun stringToArrayList(data: String?): ArrayList<Weather> {
        if (data == null) {
            return ArrayList()
        }
        val listType =
            object : TypeToken<ArrayList<Weather?>?>() {}.type
        return gSon.fromJson(
            data,
            listType
        ) ?: ArrayList()
    }

    @TypeConverter
    fun arrayListToString(someObjects: ArrayList<Weather?>?): String {
        return gSon.toJson(someObjects)
    }
}
