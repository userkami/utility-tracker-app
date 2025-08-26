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
fun AddMeterScreen(navController: NavController, viewModel: UtilityViewModel) {
    var meterId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var reading by remember { mutableStateOf("") }

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
        Button(onClick = {
            viewModel.addMeter(
                Meter(meterId, name, reading.toDoubleOrNull() ?: 0.0)
            )
            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}