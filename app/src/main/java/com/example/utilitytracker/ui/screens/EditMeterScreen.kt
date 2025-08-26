package com.example.utilitytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.viewmodel.UtilityViewModel

@Composable
fun EditMeterScreen(
    navController: NavController,
    viewModel: UtilityViewModel,
    meter: Meter
) {
    var meterId by remember { mutableStateOf(meter.meterId) }
    var name by remember { mutableStateOf(meter.friendlyName ?: "") }
    var reading by remember { mutableStateOf(meter.initialReading.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = meterId,
            onValueChange = { meterId = it },
            label = { Text("Meter ID") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Friendly Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = reading,
            onValueChange = { reading = it },
            label = { Text("Initial Reading") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                viewModel.updateMeter(
                    Meter(meterId, name.takeIf { it.isNotBlank() }, reading.toDoubleOrNull() ?: 0.0)
                )
                navController.popBackStack()
            }) {
                Text("Save")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }
        }
    }
}