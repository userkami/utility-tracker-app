package com.example.utilitytracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilitytracker.SettingsManager
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.data.repository.UtilityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UtilityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UtilityRepository(
        (application as com.example.utilitytracker.UtilityApplication).database.meterDao(),
        application.database.readingDao()
    )
    
    private val settingsManager = SettingsManager(application)

    private val _meters = MutableStateFlow<List<Meter>>(emptyList())
    val meters: StateFlow<List<Meter>> = _meters

    var theme: String
        get() = settingsManager.theme
        set(value) {
            settingsManager.theme = value
        }

    var alertThreshold: Double
        get() = settingsManager.alertThreshold
        set(value) {
            settingsManager.alertThreshold = value
        }

    var userDefinedColor: String
        get() = settingsManager.userDefinedColor
        set(value) {
            settingsManager.userDefinedColor = value
        }

    init {
        loadMeters()
    }

    private fun loadMeters() {
        viewModelScope.launch {
            _meters.value = repository.getAllMeters()
        }
    }

    fun addMeter(meter: Meter) {
        viewModelScope.launch {
            repository.insertMeter(meter)
            loadMeters()
        }
    }

    fun updateMeter(meter: Meter) {
        viewModelScope.launch {
            repository.updateMeter(meter)
            loadMeters()
        }
    }

    fun deleteMeter(meter: Meter) {
        viewModelScope.launch {
            repository.deleteMeter(meter)
            loadMeters()
        }
    }
    
    fun saveSettings(theme: String, alertThreshold: Double, userDefinedColor: String) {
        this.theme = theme
        this.alertThreshold = alertThreshold
        this.userDefinedColor = userDefinedColor
    }
}

// Add this function to UtilityViewModel
fun validateReading(
    currentReading: Double,
    previousReading: Double?,
    currentDate: Long,
    lastReadingDate: Long?
): String? {
    if (previousReading != null && currentReading <= previousReading) {
        return "Reading must be greater than previous reading"
    }
    
    if (lastReadingDate != null && currentDate < lastReadingDate) {
        return "Date cannot be before last reading"
    }
    
    return null
}