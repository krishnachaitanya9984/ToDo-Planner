package com.krinyny.todoplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krinyny.todoplanner.data.ToDoRepositoryImpl
import com.krinyny.todoplanner.data.ToDoTask
import com.krinyny.todoplanner.ui.event.TodoListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    private val _todoItems = MutableStateFlow<List<ToDoTask>>(emptyList())
    val todoItems: StateFlow<List<ToDoTask>> = _todoItems

    fun onUIEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.GetAllTasks -> getAllTasks()
            is TodoListEvent.SearchTasks -> searchTodoItems(event.searchString)
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks().collect {
                _todoItems.emit(it)
            }
        }
    }

    private fun searchTodoItems(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchTasks(search).collect {
                _todoItems.emit(it)
            }
        }
    }

}