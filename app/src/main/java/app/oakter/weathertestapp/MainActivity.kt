package app.oakter.weathertestapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.oakter.weathertestapp.data.Hourly
import app.oakter.weathertestapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), GPSLocationFetcher.GetGpsCordinates {
    private var binding: ActivityMainBinding? = null
    private var viewModel: WeatherViewModel? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var weatherRecyclerView : WeatherRecyclerView? = null
    private var hourlyData: List<Hourly>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding?.root)
        initRecyclerView()
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        GPSLocationFetcher().retrieveLocation(this, fusedLocationClient!!, this)

        observeData()
    }

    private fun observeData() {
        viewModel?.getWeatherList()?.observe(this, Observer<WeatherResponse> {
            Log.e("it", " : ::  : : ${it}")
            weatherRecyclerView?.updateList(it.hourly)
        })
    }

    override fun getcordinates(lattitude: Double, longitude: Double) {
        viewModel?.getWeatherData(lattitude, longitude, "7f6353c7f29edd943084378cb7950a60")
    }

    private fun initRecyclerView() {
        binding?.weatherRecyclerView?.setLayoutManager(LinearLayoutManager(this))
        weatherRecyclerView = WeatherRecyclerView(this, hourlyData)
        binding?.weatherRecyclerView?.adapter = weatherRecyclerView
    }
}