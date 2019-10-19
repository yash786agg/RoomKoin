package com.app.ui.birds.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.helper.UiHelper
import com.app.model.birds.BirdsEntity
import com.app.roomkoin.R
import com.app.ui.birds.adapter.BirdsAdapter
import com.app.ui.birds.viewmodel.BirdsViewModel
import kotlinx.android.synthetic.main.activity_birds.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class BirdsListActivity : AppCompatActivity() {

    // FOR DATA ---
    private val uiHelper: UiHelper by inject()
    private val birdsVM: BirdsViewModel by viewModel()
    private val birdsAdapter = BirdsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birds)

        initRecyclerView()

        fab_add.setOnClickListener { moveToNextActivity() }
    }

    override fun onResume() {
        super.onResume()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        // OBSERVABLES ---
        birdsVM.getAllBirdsData().observe(this, Observer {

            it?.let {
                Log.e("BirdsListActivity", "getAllBirdsData it: $it")
                Log.e("BirdsListActivity", "getAllBirdsData Size: ${it.size}")
                if (it.isNotEmpty()) {
                    Log.e("BirdsListActivity", "getAllBirdsData birdName: ${it[0].birdName}")
                    Log.e("BirdsListActivity", "getAllBirdsData timeStamp: ${uiHelper.convertTimeStampToDate(it[0].timeStamp)}")

                    uiHelper.sortTimeStamp(it)
                    birdsAdapter.items = it as ArrayList<BirdsEntity>
                    recylv_birds.scrollToPosition(0)
                } else recylv_birds.setEmptyView(tv_empty)
            }
        })
    }

    //Setup the adapter class for the RecyclerView
    private fun initRecyclerView() {
        recylv_birds?.layoutManager = LinearLayoutManager(this)
        recylv_birds?.adapter = birdsAdapter
    }

    private fun moveToNextActivity() {
        val intent = Intent(this, AddBirdsActivity::class.java)
        startActivity(intent)
    }
}
