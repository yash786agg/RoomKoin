package com.app.ui.birds.activity

import android.content.Intent
import android.os.Bundle
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
                if (it.isNotEmpty()) {
                    uiHelper.sortTimeStamp(it as ArrayList<BirdsEntity>)
                    birdsAdapter.items = it
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
