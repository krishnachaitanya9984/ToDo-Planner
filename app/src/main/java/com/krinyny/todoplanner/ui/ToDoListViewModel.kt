package com.krinyny.todoplanner.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krinyny.todoplanner.data.ToDoRepository
import com.krinyny.todoplanner.data.ToDoTask
import com.krinyny.todoplanner.ui.event.AddTaskEvent
import com.krinyny.todoplanner.ui.event.TodoListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _todoItems = MutableStateFlow<List<ToDoTask>>(emptyList())
    val todoItems: StateFlow<List<ToDoTask>> = _todoItems

    fun onUIEvent(event : TodoListEvent) {
        when (event) {
            is TodoListEvent.GetAllTasks -> getAllTasks()
            is TodoListEvent.SearchTasks -> searchTodoItems(event.searchString)
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _todoItems.emit(it)
            }
        }
    }

    private fun searchTodoItems(search: String) {
        viewModelScope.launch {
            repository.searchTasks(search).collect {
                _todoItems.emit(it)
            }
        }
    }

}