package com.example.utilitytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.data.model.Reading
import com.example.utilitytracker.viewmodel.UtilityViewModel
import com.example.utilitytracker.formatDate
import java.util.*

@Composable
fun MeterDetailsScreen(
    navController: NavController,
    viewModel: UtilityViewModel,
    meter: Meter
) {
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var reading by remember { mutableStateOf("") }
    var endCycle by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Meter ID: ${meter.meterId}", style = MaterialTheme.typography.h6)
        Text("Name: ${meter.friendlyName ?: "N/A"}")
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Date:")
        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = reading,
            onValueChange = { reading = it },
            label = { Text("Current Reading") }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = endCycle,
                onCheckedChange = { endCycle = it }
            )
            Text("End 200-unit cycle")
        }
        
        errorMessage?.let {
            Text(it, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row {
            Button(onClick = {
                // Validation and saving logic would go here
                navController.navigate("reading_history/${meter.meterId}")
            }) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { 
                navController.navigate("reading_history/${meter.meterId}")
            }) {
                Text("View History")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { /* Camera functionality placeholder */ },
            enabled = false
        ) {
            Text("Camera - Coming Soon")
        }
    }
}

@Composable
fun DatePicker(
    selectedDate: Long,
    onDateSelected: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate)
    
    DatePicker(
        state = datePickerState,
        modifier = Modifier.fillMaxWidth()
    )
    
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            onDateSelected(it)
        }
    }
}

// In MeterDetailsScreen, replace the Submit button with:
Button(onClick = {
    // In a real app, you'd get the previous reading from the repository
    val previousReading: Double? = null // Replace with actual previous reading
    val lastReadingDate: Long? = null // Replace with actual last reading date
    
    val error = viewModel.validateReading(
        currentReading = reading.toDoubleOrNull() ?: 0.0,
        previousReading = previousReading,
        currentDate = selectedDate,
        lastReadingDate = lastReadingDate
    )
    
    if (error != null) {
        errorMessage = error
    } else {
        errorMessage = null
        // Proceed with saving the reading
        navController.navigate("reading_history/${meter.meterId}")
    }
}) {
    Text("Submit")
}