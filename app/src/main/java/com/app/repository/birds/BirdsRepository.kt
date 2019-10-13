package com.app.repository.birds

import android.util.Log
import com.app.database.BirdsDAO

class BirdsRepository(private val birdsDAO : BirdsDAO) {

    fun birdsDAO() {
        Log.e("BirdsListActivity","BirdsRepository birdsDAO: $birdsDAO")
    }
}