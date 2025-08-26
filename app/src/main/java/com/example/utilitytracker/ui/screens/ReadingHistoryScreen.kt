package com.example.utilitytracker.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.utilitytracker.data.model.Reading
import com.example.utilitytracker.viewmodel.UtilityViewModel
import com.example.utilitytracker.formatDate
import java.io.File

@Composable
fun ReadingHistoryScreen(
    navController: NavController,
    viewModel: UtilityViewModel,
    meterId: String
) {
    var readings by remember { mutableStateOf<List<Reading>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Load readings for this meter
        // In a real app, you'd fetch from repository
        readings = listOf(
            Reading(1, meterId, System.currentTimeMillis() - 86400000, 150.0, 1, 50.0),
            Reading(2, meterId, System.currentTimeMillis() - 172800000, 100.0, 1, 30.0)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Reading History", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(readings) { reading ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Date: ${formatDate(reading.date)}")
                        Text("Reading: ${reading.reading}")
                        Text("Used Units: ${reading.usedUnits}")
                        Text("Cycle ID: ${reading.cycleId}")
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                exportToCSV(context, readings, meterId)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Export to CSV")
        }
    }
}

fun exportToCSV(context: Context, readings: List<Reading>, meterId: String) {
    val csvContent = generateCSVContent(readings, meterId)
    val fileName = "utility_readings_${meterId}_${System.currentTimeMillis()}.csv"
    
    try {
        val file = File(context.getExternalFilesDir(null), fileName)
        file.writeText(csvContent)
        
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "text/csv"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        context.startActivity(Intent.createChooser(shareIntent, "Export CSV"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun generateCSVContent(readings: List<Reading>, meterId: String): String {
    val header = "Meter ID,Date,Reading,Used Units,Cycle\n"
    val rows = readings.joinToString("\n") { reading ->
        "${meterId},${formatDate(reading.date)},${reading.reading},${reading.usedUnits},${reading.cycleId}"
    }
    return header + rows
}