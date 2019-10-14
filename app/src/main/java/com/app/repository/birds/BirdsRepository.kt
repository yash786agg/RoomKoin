package com.app.repository.birds

import com.app.dataBase.BirdsDAO
import com.app.model.birds.BirdsEntity

class BirdsRepository(private val birdsDAO : BirdsDAO) {

    suspend fun insertDataAsync(birdsEntity : BirdsEntity) = birdsDAO.insertBirdData(birdsEntity)

    suspend fun getListAsync() = birdsDAO.fetchBirdsData()
}