package com.example.utilitytracker

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var theme: String
        get() = prefs.getString("theme", "normal") ?: "normal"
        set(value) = prefs.edit().putString("theme", value).apply()

    var alertThreshold: Double
        get() = prefs.getFloat("alert_threshold", 180f).toDouble()
        set(value) = prefs.edit().putFloat("alert_threshold", value.toFloat()).apply()

    var userDefinedColor: String
        get() = prefs.getString("user_defined_color", "#FF0000") ?: "#FF0000"
        set(value) = prefs.edit().putString("user_defined_color", value).apply()
}