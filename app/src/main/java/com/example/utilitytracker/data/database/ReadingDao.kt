package com.example.utilitytracker.data.database

import androidx.room.*
import com.example.utilitytracker.data.model.Reading

@Dao
interface ReadingDao {
    @Query("SELECT * FROM readings WHERE meterId = :meterId ORDER BY date DESC")
    suspend fun getReadingsByMeter(meterId: String): List<Reading>

    @Insert
    suspend fun insertReading(reading: Reading)

    @Query("SELECT * FROM readings WHERE meterId = :meterId ORDER BY date DESC LIMIT 1")
    suspend fun getLastReading(meterId: String): Reading?

    @Delete
    suspend fun deleteReadings(readings: List<Reading>)
}