package com.example.utilitytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.viewmodel.UtilityViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: UtilityViewModel) {
    val meters by viewModel.meters.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_meter") }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(meters) { meter ->
                MeterItem(
                    meter = meter,
                    onEdit = { navController.navigate("edit_meter/${meter.meterId}") },
                    onDelete = { viewModel.deleteMeter(meter) },
                    onClick = { navController.navigate("meter_details/${meter.meterId}") }
                )
            }
        }
    }
}

@Composable
fun MeterItem(
    meter: Meter,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Meter ID: ${meter.meterId}",
                style = MaterialTheme.typography.h6
            )
            Text(text = "Name: ${meter.friendlyName ?: "N/A"}")
            Text(text = "Initial Reading: ${meter.initialReading}")
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Details")
                }
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.MoreVert,
                            contentDescription = "Menu"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            onEdit()
                            expanded = false
                        }) {
                            Text("Edit")
                        }
                        DropdownMenuItem(onClick = {
                            onDelete()
                            expanded = false
                        }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}