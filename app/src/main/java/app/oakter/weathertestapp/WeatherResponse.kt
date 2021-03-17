package app.oakter.weathertestapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.oakter.weathertestapp.data.Current
import app.oakter.weathertestapp.data.Hourly
import app.oakter.weathertestapp.data.local.room.type_converters.CurrentConverter
import app.oakter.weathertestapp.data.local.room.type_converters.HourlyListConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_table")
public class WeatherResponse {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("timezone")
    @Expose
    var timezone: String? = null

    @SerializedName("timezone_offset")
    @Expose
    var timezoneOffset: Int? = null

    @SerializedName("current")
    @Expose
    @TypeConverters(CurrentConverter::class)
    var current: Current? = null

    @SerializedName("hourly")
    @Expose
    @TypeConverters(HourlyListConverter::class)
    var hourly: List<Hourly>? = null

    @PrimaryKey(autoGenerate = true) var id: Long = 0
}
