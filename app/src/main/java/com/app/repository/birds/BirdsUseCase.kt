package com.app.repository.birds

import com.app.model.birds.BirdsEntity

class BirdsUseCase(private val birdsRepository : BirdsRepository) {

    suspend fun executeInsertQuery(birdsEntity : BirdsEntity) = birdsRepository.insertDataAsync(birdsEntity)

    suspend fun executeGetListQuery() : List<BirdsEntity> = birdsRepository.getListAsync()
}