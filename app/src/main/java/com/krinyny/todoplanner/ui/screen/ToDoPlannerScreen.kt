package com.krinyny.todoplanner.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import com.krinyny.tododb.data.ToDoTask
import com.krinyny.todoplanner.R
import com.krinyny.todoplanner.ui.viewmodel.ToDoTasksViewModel
import com.krinyny.todoplanner.util.Constants.ERROR_MESSAGE_KEY
import androidx.compose.material3.CircularProgressIndicator as CircularProgressIndicator1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoPlannerScreen(
    navHostController: NavHostController,
    savedStateHandle: SavedStateHandle,
    viewModel: ToDoTasksViewModel = hiltViewModel()
) {
    val todoItems by viewModel.todoList.collectAsState(emptyList())
    val isSearching by viewModel.isSearching.collectAsState(false)
    var searchText by rememberSaveable { mutableStateOf("") }
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        val errorMessageId: Int? = savedStateHandle.get<Int>(ERROR_MESSAGE_KEY)
        errorMessageId?.let {
            showErrorDialog = true
        }
    }


    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text(stringResource(id = R.string.app_name))
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navHostController.navigate("AddTaskScreen")
        }, content = { Icon(Icons.Default.Add, contentDescription = "Add") })
    }) {
        if (searchText.isEmpty() && todoItems.isEmpty()) {
            EmptyTask()
        } else {
            if (showErrorDialog) {
                val messageId: Int? = savedStateHandle.get<Int>(ERROR_MESSAGE_KEY)
                messageId?.let {
                    ShowErrorDialog(errorMessage = stringResource(id = messageId)) {
                        savedStateHandle[ERROR_MESSAGE_KEY] = null
                        showErrorDialog = false
                    }
                }
            } else {
                TaskPlannerList(todoItems = todoItems, isSearching) {
                    searchText = it
                    viewModel.onSearchTextChange(it)
                }
            }
        }
    }

}

@Composable
fun TaskPlannerList(
    todoItems: List<ToDoTask>,
    isSearching: Boolean,
    onTextChange: (String) -> Unit
) {
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
                    text = stringResource(id = R.string.search_task),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator1(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                )
            }
        } else {
            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(todoItems) { todo ->
                    TodoTaskItem(text = todo.taskName)
                }
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
fun EmptyTask() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(top = 108.dp),
            text = stringResource(id = R.string.empty_task_text),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ShowErrorDialog(
    errorMessage: String,
    clearErrorDialog: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = { },
        title = {
            Text(
                text = stringResource(id = R.string.error_title),
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
                    clearErrorDialog()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }

        }
    )
}