package com.app.repository.birds

import android.util.Log

class BirdsUseCase(private val birdsRepository : BirdsRepository) {

    fun birdsUseCase() {
        Log.e("BirdsListActivity","BirdsListViewModel birdsRepository: $birdsRepository")
        birdsRepository.birdsDAO()
    }
}