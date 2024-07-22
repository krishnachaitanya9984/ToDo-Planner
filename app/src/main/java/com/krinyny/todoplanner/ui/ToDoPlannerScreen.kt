package com.krinyny.todoplanner.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun ToDoPlannerScreen(
    navHostController: NavHostController,
    viewModel: ToDoViewModel
) {
    TodoMainContent(navHostController)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoMainContent(navHostController: NavHostController) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text("Todo Planner")
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navHostController.navigate("AddTaskScreen")
        }, content = { Icon(Icons.Default.Add, contentDescription = "Add") })
    }) {
        AddTodoLabelUI()
    }
}

@Composable
fun AddTodoLabelUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Press the + button to add a TODO task",
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 110.dp)
        )
    }
}