package com.app.helper

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.app.callBacks.AccessLocationListener
import com.app.callBacks.GpsEnableListener
import com.app.roomkoin.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationRequest
import com.google.android.material.snackbar.Snackbar
import java.sql.Timestamp
import java.util.*
import android.view.inputmethod.InputMethodManager
import com.app.model.birds.BirdsEntity
import java.text.SimpleDateFormat

class UiHelper(private val context : Context) {

    fun toast(content: String) = Toast.makeText(context, content, Toast.LENGTH_LONG).show()

    fun showSnackBar(view: View, content: String) = Snackbar.make(view, content, Snackbar.LENGTH_LONG).show()

    fun getTimeStamp() : Long {
        val date = Date()
        return date.time

        /*
        long time = date.getTime();
        System.out.println("Time in Milliseconds: " + time);
        Timestamp ts = new Timestamp(time);
        System.out.println("Current Time Stamp: " + ts);
        Time in Milli Seconds: 1447402821007
        Current Time Stamp: 2015-11-13 13:50:21.007*/

    }

    fun convertTimeStampToDate(time : Long?) : String {
        val timestamp = time?.let { Timestamp(it) }

        val dateFormatInput  = "yyyy-MM-dd HH:mm:ss.SSS"
        val dateFormatOutput  = "dd MMMM yyyy h:mm a"

        var spf = SimpleDateFormat(dateFormatInput, Locale.US)
        val newDate : Date? = spf.parse(timestamp.toString())
        spf = SimpleDateFormat(dateFormatOutput, Locale.US)

        return spf.format(newDate!!)
    }

    /*
     * Checking out is Google Play Services app is installed or not.
     * */

    fun isPlayServicesAvailable() : Boolean {
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

    fun isLocationProviderEnabled() : Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    fun accessLocationDialogWithListener(activity: Activity, title : String, content : String,
                                         listener : AccessLocationListener, positiveText : String,
                                         negativeText : String, cancelable : Boolean) {

        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(positiveText){ dialog, _ ->
            listener.onPositive()
            dialog.dismiss()
        }
        builder.setNegativeButton(negativeText){ dialog, _ ->
            listener.onNegative()
            dialog.dismiss()
        }
        val alert = builder.create()

        if(!alert.isShowing)
            alert.show()
    }

    fun showPositiveDialogWithListener(activity : Activity, title : String, content : String,
                                       listener : GpsEnableListener,
                                       positiveText : String, cancelable : Boolean) {
        val builder = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(content)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(positiveText){ dialog, _ ->
            listener.onPositive()
            dialog.dismiss()
        }

        val alert = builder.create()

        if(!alert.isShowing)
            alert.show()
    }

    fun showProgressBar(progressBar : ProgressBar, display : Boolean)
    {
        if(!display) progressBar.visibility = View.GONE
        else progressBar.visibility = View.VISIBLE
    }

    fun hideKeyboard(view : View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun sortTimeStamp(list : List<BirdsEntity>) {
        Collections.sort(list) { lhs, rhs -> lhs?.timeStamp?.let { rhs?.timeStamp?.compareTo(it) }!! }
    }
}