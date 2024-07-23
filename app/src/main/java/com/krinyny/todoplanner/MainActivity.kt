package com.krinyny.todoplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.krinyny.todoplanner.theme.TodoPlannerTheme
import com.krinyny.todoplanner.ui.ToDoAppNavigation
import com.krinyny.todoplanner.ui.ToDoListViewModel
import com.krinyny.todoplanner.ui.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val listViewModel: ToDoListViewModel by viewModels()
    private val addViewModel: ToDoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoPlannerTheme {
                navController = rememberNavController()
                ToDoAppNavigation(
                    navController,listViewModel,addViewModel
                )

            }
        }
    }
}