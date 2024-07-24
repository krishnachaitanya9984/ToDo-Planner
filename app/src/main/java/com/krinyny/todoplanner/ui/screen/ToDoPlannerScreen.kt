package com.krinyny.todoplanner.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import com.krinyny.tododb.data.ToDoTask
import com.krinyny.todoplanner.ui.viewmodel.ToDoListViewModel
import com.krinyny.todoplanner.ui.event.TodoListEvent
import com.krinyny.todoplanner.util.Constants.ERROR_MESSAGE_KEY

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoPlannerScreen(
    navHostController: NavHostController,
    viewModel: ToDoListViewModel,
    savedStateHandle: SavedStateHandle,
) {

    val todoItems by viewModel.todoItems.collectAsState(emptyList())
    var searchText by rememberSaveable { mutableStateOf("") }
    val showErrorDialog = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        if (searchText.isEmpty()) {
            val errorMessage = savedStateHandle.get<String>(ERROR_MESSAGE_KEY)
            if (!errorMessage.isNullOrEmpty()) {
                showErrorDialog.value = true
            } else {
                viewModel.onUIEvent(TodoListEvent.GetAllTasks)
            }
        } else {
            viewModel.onUIEvent(TodoListEvent.SearchTasks(searchText))
        }

    }

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
        if (searchText.isEmpty() && todoItems.isEmpty()) {
            NoToDoTasks()
        } else {
            if (showErrorDialog.value) {
                val message = savedStateHandle.get<String>(ERROR_MESSAGE_KEY)
                message?.let {
                    ShowErrorDialog(errorMessage = message, showErrorDialog)
                }
            } else {
                TaskPlannerList(todoItems = todoItems) {
                    searchText = it
                    if (it.isNotEmpty()) {
                        viewModel.onUIEvent(TodoListEvent.SearchTasks(it))
                    } else {
                        viewModel.onUIEvent(TodoListEvent.GetAllTasks)
                    }
                }
            }
        }
    }

}

@Composable
fun TaskPlannerList(todoItems: List<ToDoTask>, onTextChange: (String) -> Unit) {
    var searchText by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 120.dp,
                bottom = 50.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        OutlinedTextField(value = searchText,
            onValueChange = {
                searchText = it
                onTextChange(searchText)
            }, label = {
                Text(
                    text = "Search task",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
            items(todoItems) { todo ->
                TodoTaskItem(text = todo.taskName)
            }
        }
    }

}

@Composable
fun TodoTaskItem(text: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier
            .padding(
                top = 6.dp,
                bottom = 3.dp
            )
            .wrapContentSize(align = Alignment.Center),
        shape = RoundedCornerShape(corner = CornerSize(size = 10.dp))

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 10.dp
                )
        )
    }

}

@Composable
fun NoToDoTasks() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(top = 108.dp),
            text = "Press the + button to add a TODO task",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ShowErrorDialog(errorMessage: String, showErrorDialog: MutableState<Boolean>) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = { },
        title = {
            Text(
                text = "Error",
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.primary
            )
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    showErrorDialog.value = false
                }
            ) {
                Text("OK")
            }

        }
    )
}