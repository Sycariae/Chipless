package com.sycarias.chipless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sycarias.chipless.ui.screens.CreateTable
import com.sycarias.chipless.ui.screens.MainMenu
import com.sycarias.chipless.ui.theme.ChiplessTheme

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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "MainMenu",
        //enterTransition = { EnterTransition.None }
    ) {
        composable("MainMenu") { MainMenu(navController) }
        composable("CreateTable") { CreateTable(navController) }
    }
}