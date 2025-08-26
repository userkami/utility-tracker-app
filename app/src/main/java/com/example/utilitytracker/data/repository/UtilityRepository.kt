package com.example.utilitytracker.data.repository

import com.example.utilitytracker.data.database.MeterDao
import com.example.utilitytracker.data.database.ReadingDao
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.data.model.Reading

class UtilityRepository(
    private val meterDao: MeterDao,
    private val readingDao: ReadingDao
) {
    suspend fun getAllMeters() = meterDao.getAllMeters()
    suspend fun insertMeter(meter: Meter) = meterDao.insertMeter(meter)
    suspend fun updateMeter(meter: Meter) = meterDao.updateMeter(meter)
    suspend fun deleteMeter(meter: Meter) = meterDao.deleteMeter(meter)

    suspend fun getReadingsByMeter(meterId: String) = readingDao.getReadingsByMeter(meterId)
    suspend fun insertReading(reading: Reading) = readingDao.insertReading(reading)
    suspend fun getLastReading(meterId: String) = readingDao.getLastReading(meterId)
    suspend fun deleteReadings(readings: List<Reading>) = readingDao.deleteReadings(readings)
}