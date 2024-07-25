package com.krinyny.todoplanner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.krinyny.todoplanner.ui.screen.AddTaskScreen
import com.krinyny.todoplanner.ui.screen.ToDoPlannerScreen
import com.krinyny.todoplanner.ui.viewmodel.ToDoTasksViewModel
import com.krinyny.todoplanner.util.Constants.ADD_TASK_SCREEN
import com.krinyny.todoplanner.util.Constants.TODO_LIST_SCREEN

@Composable
fun ToDoAppNavigation(
    navHostController: NavHostController,
    todoViewModel: ToDoTasksViewModel
) {
    NavHost(navController = navHostController, startDestination = TODO_LIST_SCREEN) {
        composable(TODO_LIST_SCREEN) {
            ToDoPlannerScreen(navHostController, todoViewModel, it.savedStateHandle)
        }
        composable(ADD_TASK_SCREEN) {
            AddTaskScreen(navHostController, todoViewModel)
        }
    }
}