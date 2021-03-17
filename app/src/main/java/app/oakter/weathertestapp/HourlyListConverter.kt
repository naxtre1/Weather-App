package app.oakter.weathertestapp.data.local.room.type_converters

import androidx.room.TypeConverter
import app.oakter.weathertestapp.data.Hourly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HourlyListConverter {
    private val gSon = Gson()

    @TypeConverter
    public fun stringToArrayList(data: String?): List<Hourly> {
        if (data == null) {
            return ArrayList()
        }
        val listType =
            object : TypeToken<List<Hourly?>?>() {}.type
        return gSon.fromJson(
            data,
            listType
        ) ?: ArrayList()
    }

    @TypeConverter
    fun arrayListToString(someObjects: List<Hourly?>?): String {
        return gSon.toJson(someObjects)
    }
}



