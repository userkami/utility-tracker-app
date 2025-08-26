package com.example.utilitytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.utilitytracker.ui.theme.*
import com.example.utilitytracker.viewmodel.UtilityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: UtilityViewModel = viewModel()
            UtilityTrackerTheme(theme = viewModel.theme, userDefinedColor = viewModel.userDefinedColor) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavGraph(viewModel)
                }
            }
        }
    }
}

@Composable
fun UtilityTrackerTheme(
    theme: String = "normal",
    userDefinedColor: String = "#FF0000",
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        "dark" -> darkColors(
            primary = Purple200,
            primaryVariant = Purple700,
            secondary = Teal200
        )
        "gradient_blue" -> lightColors(
            primary = GradientBlueStart,
            primaryVariant = GradientBlueEnd,
            secondary = Teal200
        )
        "user_defined" -> {
            val userColor = try {
                Color(android.graphics.Color.parseColor(userDefinedColor))
            } catch (e: Exception) {
                Purple500
            }
            lightColors(
                primary = userColor,
                primaryVariant = userColor,
                secondary = Teal200
            )
        }
        else -> lightColors(
            primary = Purple500,
            primaryVariant = Purple700,
            secondary = Teal200
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}