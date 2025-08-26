package com.example.utilitytracker.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class AlertWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        // In a real app, you would check meter usage and show notifications
        delay(1000) // Simulate work
        return Result.success()
    }
}