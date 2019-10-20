package com.app.ui.birds.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.app.callBacks.AccessLocationListener
import com.app.callBacks.GpsEnableListener
import com.app.extensions.nonNull
import com.app.extensions.onQueryTextChange
import com.app.helper.PermissionHelper
import com.app.helper.PermissionHelper.Companion.ACCESS_FINE_LOCATION
import com.app.helper.PermissionHelper.Companion.PERMISSIONS_REQUEST_LOCATION
import com.app.helper.UiHelper
import com.app.location.GpsSetting
import com.app.location.LocationViewModel
import com.app.model.birds.BirdsEntity
import com.app.roomkoin.R
import com.app.ui.birds.viewmodel.BirdsViewModel
import com.app.utils.Constants.Companion.GPS_REQUEST_LOCATION
import kotlinx.android.synthetic.main.activty_add_birds.*
import kotlinx.android.synthetic.main.appbar.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBirdsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,View.OnClickListener
    , PermissionHelper.OnPermissionRequested
{
    // FOR DATA ---
    private val uiHelper : UiHelper by inject()
    private val locationVM : LocationViewModel by viewModel()
    private val birdsVM : BirdsViewModel by viewModel()
    private var gpsSetting : GpsSetting? = null
    private var permissionHelper : PermissionHelper? = null
    private var isGpsPermissionEnabled : Boolean = false
    private var isPermissionPermanentlyDenied = false
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_add_birds)

        btn_save.setOnClickListener(this)
        spinner_rarity.onItemSelectedListener = this

        /* Initialization of Rarity Adapter for Spinner */
        val spAdapter = ArrayAdapter.createFromResource(this, R.array.rarity_bird_array, R.layout.spinner_item)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner_rarity.adapter = spAdapter

        edt_name.onQueryTextChange {
            if(!TextUtils.isEmpty(it)) inputxt_name.error = null
            else inputxt_name.error = resources.getString(R.string.error_bird_name)
        }

        edt_notes.onQueryTextChange {
            if(!TextUtils.isEmpty(it)) inputxt_notes.error = null
            else inputxt_notes.error = resources.getString(R.string.error_notes)
        }

        btn_cancel.setOnClickListener { finish() }
        btn_back.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()

        if(isPermissionPermanentlyDenied && isGpsPermissionEnabled) checkPermissionGranted()
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            R.id.btn_save -> {
                uiHelper.hideKeyboard(view)
                validateInputData()
            }
        }
    }

    private fun validateInputData() {

        if(!TextUtils.isEmpty(edt_name.text.toString())) {
            if(!TextUtils.isEmpty(edt_notes.text.toString())) accessGeoLocation()
            else {
                inputxt_notes.error = resources.getString(R.string.error_notes)
                uiHelper.showSnackBar(rootView_add_birds,resources.getString(R.string.error_notes))
            }
        }
        else {
            inputxt_name.error = resources.getString(R.string.error_bird_name)
            uiHelper.showSnackBar(rootView_add_birds,resources.getString(R.string.error_bird_name))
        }
    }

    private fun saveBirdData(latitude : Double, longitude : Double) {
        val timeStamp : Long = uiHelper.getTimeStamp()
        val birdName : String = edt_name.text.toString()
        val notes : String = edt_notes.text.toString()
        val birdRarity : String = spinner_rarity.selectedItem.toString()

        val birdsEntity = BirdsEntity(timeStamp,birdName,notes,birdRarity,latitude,longitude)
        birdsVM.addBirdsData(birdsEntity)

        uiHelper.toast(resources.getString(R.string.msg_data_added_successfully))
        finish()
    }
    private fun accessGeoLocation() {
        uiHelper.accessLocationDialogWithListener(this,
            resources.getString(R.string.msg_save_geo_location),
            resources.getString(R.string.msg_geo_location_content),
            object : AccessLocationListener {
                override fun onNegative() = saveBirdData(latitude, longitude)
                override fun onPositive() = allowLocation()
            }, resources.getString(R.string.msg_yes), resources.getString(R.string.msg_no), false)
    }

    private fun allowLocation() {
        if(uiHelper.isPlayServicesAvailable()) {
            gpsSetting = GpsSetting(this,uiHelper)

            permissionHelper = PermissionHelper(this,uiHelper)

            isGpsPermissionEnabled = true

            if(!permissionHelper?.isPermissionGranted(ACCESS_FINE_LOCATION)!!)
                permissionHelper?.requestPermission(arrayOf(ACCESS_FINE_LOCATION),PERMISSIONS_REQUEST_LOCATION,this)
            else enableGps()
        }
        else {
            saveBirdData(latitude,longitude)
            uiHelper.toast(resources.getString(R.string.error_play_service_not_installed))
        }
    }

    /*
     * Checking whether Location Permission is granted or not.
     * */

    private fun checkPermissionGranted() {
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissionHelper?.openSettingsDialog()
        else enableGps()
    }

    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<out String>, grantResults : IntArray) {
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * This function is to get the result form [PermissionHelper] class
     *
     * @param isPermissionGranted the [Boolean]
     */

    override fun onPermissionResponse(isPermissionGranted : Boolean) {

        if(!isPermissionGranted) isPermissionPermanentlyDenied = true
        else enableGps()
    }

    private fun enableGps() {
        isPermissionPermanentlyDenied = false

        if (!uiHelper.isLocationProviderEnabled()) subscribeLocationObserver()
        else gpsSetting?.openGpsSettingDialog()
    }

    // Start Observing the User Current Location and set the marker to it.
    private fun subscribeLocationObserver()
    {
        uiHelper.showProgressBar(pb_add_birds,true)

        // OBSERVABLES ---
        locationVM.currentLocation.nonNull().observe(this, Observer {

            uiHelper.showProgressBar(pb_add_birds,false)
            latitude = it.latitude
            longitude = it.longitude

            saveBirdData(latitude,longitude)

            locationVM.stopLocationUpdates()
        })

        locationVM.requestLocationUpdates()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GPS_REQUEST_LOCATION ->
                when (resultCode) {
                    RESULT_OK -> subscribeLocationObserver()

                    RESULT_CANCELED -> {
                        uiHelper.showPositiveDialogWithListener(this,
                            resources.getString(R.string.need_location),
                            resources.getString(R.string.location_content),
                            object : GpsEnableListener {
                                override fun onPositive() {
                                    enableGps()
                                }
                            }, resources.getString(R.string.turn_on), false)
                    }
                }
        }
    }

    override fun onItemSelected(adapterView : AdapterView<*>?, view: View?, int : Int, long : Long) {
        spinner_rarity.setSelection(int)
    }

    override fun onNothingSelected(adapterView : AdapterView<*>?) {}
}