package com.app.helper

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.app.callBacks.AccessLocationListener
import com.app.callBacks.GpsEnableListener
import com.app.model.birds.BirdsEntity
import com.app.roomkoin.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationRequest
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator

class UiHelper(private val context: Context) {

    fun toast(content: String) = Toast.makeText(context, content, Toast.LENGTH_LONG).show()

    fun showSnackBar(view: View, content: String) =
        Snackbar.make(view, content, Snackbar.LENGTH_LONG).show()

    fun getTimeStamp(): Long {
        val date = Date()
        return date.time
    }

    fun convertTimeStampToDate(time: Long): String {
        val dateFormatOutput = "dd MMMM yyyy h:mm a"
        val spf = SimpleDateFormat(dateFormatOutput, Locale.US)
        val newDate = Date(time)
        return spf.format(newDate)
    }

    /*
     * Checking out is Google Play Services app is installed or not.
     * */

    fun isPlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return ConnectionResult.SUCCESS == status
    }

    fun getLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3000
        return locationRequest
    }

    fun isLocationProviderEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun accessLocationDialogWithListener(
        activity: Activity, title: String, content: String,
        listener: AccessLocationListener, positiveText: String,
        negativeText: String, cancelable: Boolean
    ) {

        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(positiveText) { dialog, _ ->
            listener.onPositive()
            dialog.dismiss()
        }
        builder.setNegativeButton(negativeText) { dialog, _ ->
            listener.onNegative()
            dialog.dismiss()
        }
        val alert = builder.create()

        if (!alert.isShowing)
            alert.show()
    }

    fun showPositiveDialogWithListener(
        activity: Activity, title: String, content: String,
        listener: GpsEnableListener,
        positiveText: String, cancelable: Boolean
    ) {
        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(positiveText) { dialog, _ ->
            listener.onPositive()
            dialog.dismiss()
        }

        val alert = builder.create()

        if (!alert.isShowing)
            alert.show()
    }

    fun showProgressBar(progressBar: ProgressBar, display: Boolean) {
        if (!display) progressBar.visibility = View.GONE
        else progressBar.visibility = View.VISIBLE
    }

    fun hideKeyboard(view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun sortTimeStamp(list: ArrayList<BirdsEntity>) =
        list.sortWith(Comparator { lhs, rhs -> rhs.timeStamp.compareTo(lhs.timeStamp) })
}