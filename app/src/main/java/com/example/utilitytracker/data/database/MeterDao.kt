package com.example.utilitytracker.data.database

import androidx.room.*
import com.example.utilitytracker.data.model.Meter

@Dao
interface MeterDao {
    @Query("SELECT * FROM meters")
    suspend fun getAllMeters(): List<Meter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeter(meter: Meter)

    @Update
    suspend fun updateMeter(meter: Meter)

    @Delete
    suspend fun deleteMeter(meter: Meter)
}