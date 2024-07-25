package com.krinyny.todoplanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.krinyny.todoplanner.theme.TodoPlannerTheme
import com.krinyny.todoplanner.ui.viewmodel.ToDoTasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val todoViewModel: ToDoTasksViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoPlannerTheme {
                navController = rememberNavController()
                ToDoAppNavigation(
                    navController, todoViewModel
                )

            }
        }
    }
}