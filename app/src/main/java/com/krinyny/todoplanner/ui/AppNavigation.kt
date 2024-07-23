package com.krinyny.todoplanner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.krinyny.todoplanner.ui.screen.AddTaskScreen
import com.krinyny.todoplanner.ui.screen.ToDoPlannerScreen
import com.krinyny.todoplanner.ui.viewmodel.ToDoListViewModel
import com.krinyny.todoplanner.ui.viewmodel.AddToDoViewModel

@Composable
fun ToDoAppNavigation(
    navHostController: NavHostController,
    listViewModel: ToDoListViewModel,
    viewModel: AddToDoViewModel
) {
    NavHost(navController = navHostController, startDestination = "ToDoPlannerScreen") {
        composable("ToDoPlannerScreen") {
            ToDoPlannerScreen(navHostController, listViewModel, it.savedStateHandle)
        }
        composable("AddTaskScreen") {
            AddTaskScreen(navHostController, viewModel)
        }
    }
}