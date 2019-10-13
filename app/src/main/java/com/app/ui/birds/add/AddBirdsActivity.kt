package com.app.ui.birds.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.app.extensions.onQueryTextChange
import com.app.helper.UiHelper
import com.app.roomkoin.R
import kotlinx.android.synthetic.main.activty_add_birds.*
import org.koin.android.ext.android.inject

class AddBirdsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,View.OnClickListener
{
    // FOR DATA ---
    private val uiHelper : UiHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_add_birds)

        btn_save.setOnClickListener(this)
        spinnerRarity.onItemSelectedListener = this

        /* Initialization of Rarity Adapter for Spinner */
        val spAdapter = ArrayAdapter.createFromResource(this, R.array.rarity_bird_array, R.layout.spinner_item)
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerRarity.adapter = spAdapter

        edTxtName.onQueryTextChange {
            if(!TextUtils.isEmpty(it)) inputTxtName.error = null
            else inputTxtName.error = resources.getString(R.string.error_bird_name)
        }

        edTxtNotes.onQueryTextChange {
            if(!TextUtils.isEmpty(it)) inputTxtNotes.error = null
            else inputTxtNotes.error = resources.getString(R.string.error_notes)
        }
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            R.id.btn_save -> saveBirdData()
        }
    }

    private fun saveBirdData() {

        if(!TextUtils.isEmpty(edTxtName.text.toString())) {
            if(!TextUtils.isEmpty(edTxtNotes.text.toString())) {
                //spinnerRarity.selectedItem.toString()
            }
            else {
                inputTxtNotes.error = resources.getString(R.string.error_notes)
                uiHelper.showSnackBar(rootView_add_birds,resources.getString(R.string.error_notes))
            }
        }
        else {
            inputTxtName.error = resources.getString(R.string.error_bird_name)
            uiHelper.showSnackBar(rootView_add_birds,resources.getString(R.string.error_bird_name))
        }
    }

    override fun onItemSelected(adapterView : AdapterView<*>?, view: View?, int : Int, long : Long) {
        spinnerRarity.setSelection(int)
    }

    override fun onNothingSelected(adapterView : AdapterView<*>?) {}
}