package com.krinyny.todoplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krinyny.tododb.data.ToDoRepositoryImpl
import com.krinyny.tododb.data.ToDoTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    repository: ToDoRepositoryImpl
) : ViewModel() {

    private val _searchText = MutableStateFlow("")

    private val searchTodoQuery: StateFlow<String> =
        _searchText
            .debounce(2000)
            .stateIn(viewModelScope, SharingStarted.Lazily, "")

    val todoList: StateFlow<List<ToDoTask>> =
        repository.getAllTasks()
            .combine(searchTodoQuery) { tasks, query ->
                if (query.isEmpty()) {
                    tasks
                } else {
                    tasks.filter { it.taskName.contains(query, ignoreCase = true) }
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun onSearchTextChange(text: String) {
        _searchText.update { text }
    }

}