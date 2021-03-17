package app.oakter.weathertestapp.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import app.oakter.weathertestapp.R
import app.oakter.weathertestapp.ui.MainActivity


class SimpleAppWidget : AppWidgetProvider() {

    private var service: PendingIntent? = null


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context,
                                appWidgetManager: AppWidgetManager,
                                appWidgetId: Int) {
        Log.e(javaClass.simpleName, "updating")
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, UpdateService::class.java)

        if (service == null) {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT)
        }
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.widget_main)
        //Construct an Intent object includes web adresss.
        val intent = Intent(context, MainActivity::class.java)
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent)
        //Instruct the widget manager to update the widget
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, service)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}