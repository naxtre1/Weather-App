package app.oakter.weathertestapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.oakter.weathertestapp.data.remote.beans.Hourly
import app.oakter.weathertestapp.databinding.HourlyRecyclerViewBinding
import java.text.SimpleDateFormat
import java.util.*


class WeatherRecyclerViewAdapter(val context: Context, var hourlyData: List<Hourly>?) :
    RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder>() {
    private var binding: HourlyRecyclerViewBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(context)
        binding = HourlyRecyclerViewBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.binding.temperatureToday.text = "${hourlyData?.get(position)?.temp.toString()}F"
        holder.binding.humidityToday.text = hourlyData?.get(position)?.humidity.toString()
        holder.binding.windspeedToday.text = hourlyData?.get(position)?.windSpeed.toString()
        holder.binding.hourTime.text = hourlyData?.get(position)?.dt?.let { getDateTime(it) }
    }

    override fun getItemCount(): Int {
        return if (hourlyData != null) {
            val size = (hourlyData!!.size / 2) - 1
            if (size > 0) size else 0
        } else
            0
    }

    class WeatherViewHolder(var binding: HourlyRecyclerViewBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

    }

    private fun getDateTime(timeStamp: Int): String {
        try {
            //Unix seconds

            //Unix seconds
            val unix_seconds: Long = timeStamp.toLong()
            //convert seconds to milliseconds
            //convert seconds to milliseconds
            val date = Date(timeStamp * 1000L)
            // format of the date
            // format of the date
            val jdf = SimpleDateFormat("HH:mm:ss", Locale.ROOT)
            jdf.timeZone = TimeZone.getTimeZone("IST")
            val java_date = jdf.format(date)
            return java_date
        } catch (e: Exception) {
            return e.toString()
        }
    }

    fun updateList(updatedList: List<Hourly>?) {
        hourlyData = updatedList
        notifyDataSetChanged()
    }


}