package com.example.utilitytracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meters")
data class Meter(
    @PrimaryKey val meterId: String,
    val friendlyName: String?,
    val initialReading: Double
)