package com.app.database

import androidx.room.Dao
import androidx.room.Insert
import com.app.model.birds.BirdsEntity

@Dao
interface BirdsDAO {

    /**
     * Save Bird Data birdsEntity
     */
    @Insert
    fun saveBirdData(birdsEntity : BirdsEntity)
}