package com.app.model.birds

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.utils.Constants.Companion.BIRD_NAME
import com.app.utils.Constants.Companion.BIRD_NOTES
import com.app.utils.Constants.Companion.BIRD_RARITY
import com.app.utils.Constants.Companion.LATITUDE
import com.app.utils.Constants.Companion.LONGITUDE
import com.app.utils.Constants.Companion.TABLE_NAME
import com.app.utils.Constants.Companion.TIME_STAMP

@Entity(tableName = TABLE_NAME)
data class BirdsEntity (@PrimaryKey @ColumnInfo(name = TIME_STAMP) val timeStamp : Long,
                        @ColumnInfo(name = BIRD_NAME) val birdName : String?,
                        @ColumnInfo(name = BIRD_NOTES) val notes : String?,
                        @ColumnInfo(name = BIRD_RARITY) val birdRarity : String?,
                        @ColumnInfo(name = LATITUDE) val latitude : Double?,
                        @ColumnInfo(name = LONGITUDE) val longitude : Double?)