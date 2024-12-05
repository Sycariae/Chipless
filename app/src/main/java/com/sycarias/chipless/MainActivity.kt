package com.sycarias.chipless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sycarias.chipless.ui.screens.CreateTableScreen
import com.sycarias.chipless.ui.screens.GameTableScreen
import com.sycarias.chipless.ui.screens.MainMenuScreen
import com.sycarias.chipless.ui.theme.ChiplessTheme
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
data class GameTable(
    val activeDealerId: State<Int?>
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainMenu
    ) {
        composable<MainMenu> { MainMenuScreen(navController) }
        composable<CreateTable> { CreateTableScreen(navController) }
        composable<GameTable> { backStackEntry ->
            val gameTable: GameTable = backStackEntry.toRoute()
            GameTableScreen(navController, gameTable.activeDealerId)
        }
    }
}