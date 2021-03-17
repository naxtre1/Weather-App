package app.oakter.weathertestapp

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class UpdateService : Service(), GPSLocationFetcher.GetGpsCordinates {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // generates random number
        this.getData()
        // Reaches the view on widget and displays the number

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getData() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        GPSLocationFetcher().retrieveLocation(applicationContext, fusedLocationClient!!, this)
    }

    override fun getcordinates(lattitude: Double, longitude: Double) {
        NetworkModule.providePokemonApiService().getWeather(lattitude, longitude, "minutely,daily,alerts", "imperial","7f6353c7f29edd943084378cb7950a60")
            ?.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                run {
                    val view = RemoteViews(packageName, R.layout.widget_main)
                    view.setTextViewText(R.id.tvWidget, result.current?.temp.toString())
                    val theWidget = ComponentName(this, SimpleAppWidget::class.java)
                    val manager = AppWidgetManager.getInstance(this)
                    manager.updateAppWidget(theWidget, view)
                }
            }
            ) { error -> Log.e("","weather: " + error.message) }
    }
}