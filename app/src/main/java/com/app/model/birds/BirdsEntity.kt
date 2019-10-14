package com.app.model.birds

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.utils.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class BirdsEntity (@PrimaryKey @ColumnInfo(name = "timeStamp") val timeStamp : Long?,
                        @ColumnInfo(name = "birdName") val birdName : String?,
                        @ColumnInfo(name = "notes") val notes : String?,
                        @ColumnInfo(name = "birdRarity") val birdRarity : String?,
                        @ColumnInfo(name = "latitude") val latitude : Double?,
                        @ColumnInfo(name = "longitude") val longitude : Double?)