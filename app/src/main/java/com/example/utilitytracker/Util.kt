package com.example.utilitytracker

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Long): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
}