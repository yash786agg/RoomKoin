package com.app.model.birds

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.utils.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class BirdsEntity (@PrimaryKey val timeStamp : Long)