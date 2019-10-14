package com.app.ui.birds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.base.BaseViewModel
import com.app.model.birds.BirdsEntity
import com.app.repository.birds.BirdsUseCase
import kotlinx.coroutines.launch

class BirdsViewModel(private val birdsUseCase : BirdsUseCase) : BaseViewModel() {

    // FOR DATA --
    private lateinit var data : MutableLiveData<List<BirdsEntity>>

    fun addBirdsData(birdsEntity : BirdsEntity)  {
        ioScope.launch { birdsUseCase.executeInsertQuery(birdsEntity) }
    }

    fun getAllBirdsData() : LiveData<List<BirdsEntity>> {
        data = MutableLiveData()

        ioScope.launch { data.postValue(birdsUseCase.executeGetListQuery()) }

        return data
    }
}