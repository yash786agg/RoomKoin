package com.app.ui.birds.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.roomkoin.R
import com.app.ui.birds.add.AddBirdsActivity
import kotlinx.android.synthetic.main.activity_birds.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BirdsListActivity : AppCompatActivity() {

    // FOR DATA ---
    private val birdsListVM : BirdsListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birds)

        Log.e("BirdsListActivity","BirdsListActivity birdsListVM: $birdsListVM")

        birdsListVM.birdsviewModel()

        fab_add.setOnClickListener { moveToNextActivity()}
    }

    private fun moveToNextActivity() {

        val intent = Intent(this, AddBirdsActivity::class.java)
        startActivity(intent)
    }
}
