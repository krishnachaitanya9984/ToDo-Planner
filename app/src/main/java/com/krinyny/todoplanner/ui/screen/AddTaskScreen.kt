package com.krinyny.todoplanner.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.krinyny.todoplanner.ui.viewmodel.AddToDoViewModel
import com.krinyny.todoplanner.ui.event.AddTaskEvent
import com.krinyny.todoplanner.ui.state.AddTaskScreenState
import com.krinyny.todoplanner.util.Constants.ERROR_MESSAGE_KEY
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTaskScreen(
    navHostController: NavHostController,
    viewModel: AddToDoViewModel
) {
    AddTaskContent(navHostController, viewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskContent(
    navHostController: NavHostController,
    viewModel: AddToDoViewModel
) {
    var isLoading = viewModel.isLoading.collectAsState().value
    val focusManager = LocalFocusManager.current
    var taskName by remember { mutableStateOf("") }
    Log.e("KC", "AddTaskContent")
    LaunchedEffect(key1 = true) {
        viewModel.screenStateFlow.collectLatest { state ->
            focusManager.clearFocus()
            when (state) {
                is AddTaskScreenState.GoBack -> {
                    navHostController.navigateUp()
                }

                is AddTaskScreenState.GoBackWithErrorMessage -> {
                    navHostController.apply {
                        previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(ERROR_MESSAGE_KEY, state.errorMessage)
                        navigateUp()
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text("Add Todo Task")
            })
        }
    ) {
        Log.e("KC", "isLoading : $isLoading")
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 108.dp, start = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    label = { Text(text = "Enter new Todo") },
                    value = taskName,
                    onValueChange = { taskName = it },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.onUIEvent(AddTaskEvent.AddToDoTask(taskName)) },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Add TODO")
                }
            }
        }


    }
}