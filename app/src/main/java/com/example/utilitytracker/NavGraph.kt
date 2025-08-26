package com.example.utilitytracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.utilitytracker.data.model.Meter
import com.example.utilitytracker.ui.screens.*
import com.example.utilitytracker.viewmodel.UtilityViewModel

@Composable
fun NavGraph(viewModel: UtilityViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController, viewModel)
        }
        composable("add_meter") {
            AddMeterScreen(navController, viewModel)
        }
        composable(
            "edit_meter/{meterId}",
            arguments = listOf(navArgument("meterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val meterId = backStackEntry.arguments?.getString("meterId") ?: ""
            // In a real app, you'd fetch the meter from the repository
            val dummyMeter = Meter(meterId, "Dummy Meter", 0.0)
            EditMeterScreen(navController, viewModel, dummyMeter)
        }
        composable(
            "meter_details/{meterId}",
            arguments = listOf(navArgument("meterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val meterId = backStackEntry.arguments?.getString("meterId") ?: ""
            // In a real app, you'd fetch the meter from the repository
            val dummyMeter = Meter(meterId, "Dummy Meter", 0.0)
            MeterDetailsScreen(navController, viewModel, dummyMeter)
        }
        composable(
            "reading_history/{meterId}",
            arguments = listOf(navArgument("meterId") { type = NavType.StringType })
        ) { backStackEntry ->
            val meterId = backStackEntry.arguments?.getString("meterId") ?: ""
            ReadingHistoryScreen(navController, viewModel, meterId)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
		composable("settings") {
    SettingsScreen(navController, viewModel)
}
    }
}