package com.app.dataBase

import androidx.room.Dao
import androidx.room.Insert
import com.app.model.birds.BirdsEntity
import androidx.room.Query

@Dao
interface BirdsDAO {

    /**
     * Save Bird Data birdsEntity
     */
    @Insert
    suspend fun insertBirdData(birdsEntity : BirdsEntity)

    /**
     * Get all the data of BirdsEntity
     */

    @Query("SELECT * FROM BIRDS")
    suspend fun fetchBirdsData() : List<BirdsEntity>
}