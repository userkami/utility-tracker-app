package com.example.utilitytracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "readings")
data class Reading(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val meterId: String,
    val date: Long,
    val reading: Double,
    val cycleId: Int,
    val usedUnits: Double
)