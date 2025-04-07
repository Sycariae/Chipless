package com.sycarias.chipless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sycarias.chipless.ui.screens.CreateTableScreen
import com.sycarias.chipless.ui.screens.GameTableScreen
import com.sycarias.chipless.ui.screens.MainMenuScreen
import com.sycarias.chipless.ui.screens.SettingsScreen
import com.sycarias.chipless.ui.theme.ChiplessTheme
import com.sycarias.chipless.viewModel.ViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChiplessTheme {
                AppNavigation()
            }
        }
    }
}

@Serializable
object MainMenu

@Serializable
object CreateTable

@Serializable
object GameTable

@Serializable
object Settings

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MainMenu
    ) {
        composable<MainMenu> {
            MainMenuScreen(navController)
        }
        composable<Settings> {
            SettingsScreen(navController)
        }
        composable<CreateTable> {
            CreateTableScreen(navController, viewModel)
        }
        composable<GameTable> {
            GameTableScreen(navController, viewModel)
        }
    }
}