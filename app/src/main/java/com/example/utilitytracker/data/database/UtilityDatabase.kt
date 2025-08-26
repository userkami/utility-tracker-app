package com.example.utilitytracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.data.model.Reading

@Database(entities = [Meter::class, Reading::class], version = 1, exportSchema = false)
abstract class UtilityDatabase : RoomDatabase() {
    abstract fun meterDao(): MeterDao
    abstract fun readingDao(): ReadingDao

    companion object {
        @Volatile
        private var INSTANCE: UtilityDatabase? = null

        fun getDatabase(context: Context): UtilityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UtilityDatabase::class.java,
                    "utility_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}