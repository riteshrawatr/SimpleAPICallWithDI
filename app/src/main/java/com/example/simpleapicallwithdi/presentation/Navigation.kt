package com.example.simpleapicallwithdi.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class Navigation {
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val vm: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = NavScreen.Scanner.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = NavScreen.Main.route) {
            MainScreen(vm,navController)
        }

        composable(route = NavScreen.Scanner.route) {
            Scanner(vm,/*navController*/)
        }
    }
}

sealed class NavScreen(
    val route: String
) {
    object Main : NavScreen("main")
    object Scanner : NavScreen("scanner")
}