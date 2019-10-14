package com.app.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.model.birds.BirdsEntity

@Database(entities = [BirdsEntity::class], version = 1)
abstract class BirdsDatabase : RoomDatabase() {

    abstract fun birdsDAO() : BirdsDAO
}