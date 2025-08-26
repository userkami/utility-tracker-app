package com.example.utilitytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.utilitytracker.viewmodel.UtilityViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: UtilityViewModel) {
    var selectedTheme by remember { mutableStateOf(viewModel.theme) }
    var alertThreshold by remember { mutableStateOf(viewModel.alertThreshold.toString()) }
    var userDefinedColor by remember { mutableStateOf(viewModel.userDefinedColor) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Theme Selection:")
        RadioButtonRow(
            text = "Normal",
            selected = selectedTheme == "normal",
            onClick = { selectedTheme = "normal" }
        )
        RadioButtonRow(
            text = "Dark",
            selected = selectedTheme == "dark",
            onClick = { selectedTheme = "dark" }
        )
        RadioButtonRow(
            text = "Professional Gradient Blue",
            selected = selectedTheme == "gradient_blue",
            onClick = { selectedTheme = "gradient_blue" }
        )
        RadioButtonRow(
            text = "User Defined",
            selected = selectedTheme == "user_defined",
            onClick = { selectedTheme = "user_defined" }
        )
        
        if (selectedTheme == "user_defined") {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = userDefinedColor,
                onValueChange = { userDefinedColor = it },
                label = { Text("Custom Color (Hex)") }
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("Alert Threshold:")
        OutlinedTextField(
            value = alertThreshold,
            onValueChange = { alertThreshold = it },
            label = { Text("Threshold (units)") }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                viewModel.saveSettings(
                    selectedTheme,
                    alertThreshold.toDoubleOrNull() ?: 180.0,
                    userDefinedColor
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save Settings")
        }
    }
}

@Composable
fun RadioButtonRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}