package com.app.ui.birds.list

import android.util.Log
import com.app.base.BaseViewModel
import com.app.repository.birds.BirdsUseCase

class BirdsListViewModel(private val birdsUseCase : BirdsUseCase) : BaseViewModel() {

    fun birdsviewModel() {
        Log.e("BirdsListActivity","BirdsListViewModel birdsUseCase: $birdsUseCase")
        birdsUseCase.birdsUseCase()
    }
}