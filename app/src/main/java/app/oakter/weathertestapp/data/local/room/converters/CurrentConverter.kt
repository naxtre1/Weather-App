package app.oakter.weathertestapp.data.local.room.converters

import androidx.room.TypeConverter
import app.oakter.weathertestapp.data.remote.beans.Current
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentConverter {
    private val gSon = Gson()

    @TypeConverter
    public fun stringToCurrent(data: String?): Current {
        if (data == null) {
            return Current()
        }
        val listType =
            object : TypeToken<Current?>() {}.type
        return gSon.fromJson(
            data,
            listType
        ) ?: Current()
    }

    @TypeConverter
    fun currentToString(someObjects: Current?): String {
        return gSon.toJson(someObjects)
    }
}