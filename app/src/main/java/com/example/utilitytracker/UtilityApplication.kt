package com.example.utilitytracker

import android.app.Application
import androidx.room.Room
import com.example.utilitytracker.data.database.UtilityDatabase

class UtilityApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            UtilityDatabase::class.java,
            "utility_database"
        ).build()
    }
}