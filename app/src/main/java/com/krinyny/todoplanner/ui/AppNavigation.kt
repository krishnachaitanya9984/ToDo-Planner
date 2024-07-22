package com.krinyny.todoplanner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ToDoAppNavigation(
    navHostController: NavHostController,
    viewModel: ToDoViewModel
) {
    NavHost(navController = navHostController, startDestination = "ToDoPlannerScreen") {
        composable("ToDoPlannerScreen") {
            ToDoPlannerScreen(navHostController, viewModel)
        }
        composable("AddTaskScreen") {
            AddTaskScreen(navHostController, viewModel)
        }
    }
}