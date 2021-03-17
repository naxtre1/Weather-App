package app.oakter.weathertestapp.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.TextView
import app.oakter.weathertestapp.R
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*


class GPSLocationFetcher {
    interface GetGpsCordinates {

        fun getcordinates(lattitude: Double, longitude: Double)
    }

    interface PermissionGrantedCallback {

        fun onGrantedPermission()
    }

    interface GPSEnabled {
        fun gpsEnabled()
    }
    val PermisionRequestCode: Int = 0x12

    fun checkPermissions(context: Activity, fusedLocationClient: FusedLocationProviderClient, listener: GetGpsCordinates) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                getLocation(fusedLocationClient, listener)
            } else {
                requestPermissions(context)
            }
        } else {
            getLocation(fusedLocationClient, listener)
        }

    }
    fun checkPermissions(context: Context, fusedLocationClient: FusedLocationProviderClient, listener: GetGpsCordinates) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                getLocation(fusedLocationClient, listener)
            }
        } else {
            getLocation(fusedLocationClient, listener)
        }

    }


    @SuppressLint("MissingPermission")
    private fun getLocation(fusedLocationClient: FusedLocationProviderClient, listener: GetGpsCordinates) {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // GPS location can be null if GPS is switched off
                    if (location != null) {

                        listener.getcordinates(location.latitude, location.longitude)
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("MapDemoActivity", "Error trying to get last GPS location")
                    e.printStackTrace()
                }

    }


    fun retrieveLocation(context: Activity,
                         fusedLocationClient: FusedLocationProviderClient,
                         listener: GetGpsCordinates
    ) {

        displayLocationSettingsRequest(context, object : GPSEnabled {
            override fun gpsEnabled() {
                checkPermissions(context, fusedLocationClient, listener)
            }

        })


    }

    fun retrieveLocation(context: Context,
                         fusedLocationClient: FusedLocationProviderClient,
                         listener: GetGpsCordinates
    ) {

        displayLocationSettingsRequest(context, object : GPSEnabled {
            override fun gpsEnabled() {
                checkPermissions(context, fusedLocationClient, listener)
            }

        })


    }

    private fun requestPermissions(context: Activity) {
        ActivityCompat.requestPermissions(context,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PermisionRequestCode)
    }


    fun displayLocationSettingsRequest(context: Context, listener: GPSEnabled) {
        val googleApiClient = GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build()
        googleApiClient.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(object : ResultCallback<LocationSettingsResult> {
            var TAG = "Oakter Gps "

            override fun onResult(result: LocationSettingsResult) {
                val status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                        Log.i(TAG, "All location settings are satisfied.")
                        listener.gpsEnabled()
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ")

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(context as Activity, 12)
                        } catch (e: IntentSender.SendIntentException) {
                            Log.i(TAG, "PendingIntent unable to execute request.")
                        }

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.")
                }
            }
        })
    }


    fun onRequestPermissionsResult(grantResults: IntArray,
                                   context: Activity,
                                   permissionGrantedCallback: PermissionGrantedCallback,
                                   permissions: Array<String>) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGrantedCallback.onGrantedPermission()


        } else {

//            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permissions.get(0))) {


            showPermissionSneakbar(context)

//            } else {
//                    AlertDialog.Builder(context)
//                            .setTitle("Permission Disabled")
//                            .setMessage("Please enable the permission")
//                            .setPositiveButton("Go to settings", object: DialogInterface.OnClickListener {
//                                override fun onClick(p0: DialogInterface?, p1: Int) {
//                                    val uri = Uri.fromParts("package", context.packageName, null)
//                                    context.startActivity(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri))
//                                }
//
//                            })
//                            .setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
//                                override fun onClick(p0: DialogInterface?, p1: Int) {
//                                    p0!!.cancel()
//                                    context.finish()
//
//                                }
//                            })
//                            .show()
//
//            }
//

        }

    }

    fun showPermissionSneakbar(context: Activity) {

        val snackbar = Snackbar.make(context.findViewById(android.R.id.content),
                context.resources.getString(R.string.permission_req), Snackbar.LENGTH_INDEFINITE)
                .setAction("Settings", object : View.OnClickListener {
                    override fun onClick(view: View) {
                        val uri = Uri.fromParts("package", context.packageName, null)

                        context.startActivityForResult(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri), 123)

                    }
                })

        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 5
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.show()

    }


    fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }


}