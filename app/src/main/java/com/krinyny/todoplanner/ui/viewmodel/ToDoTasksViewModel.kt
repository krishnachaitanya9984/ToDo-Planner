package com.krinyny.todoplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krinyny.tododb.data.TaskException
import com.krinyny.tododb.data.ToDoRepositoryImpl
import com.krinyny.tododb.data.ToDoTask
import com.krinyny.todoplanner.ui.state.AddTaskScreenState
import com.krinyny.todoplanner.util.Constants.LOADING_DELAY
import com.krinyny.todoplanner.util.Constants.SEARCH_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoTasksViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _screenStateFlow = MutableSharedFlow<AddTaskScreenState>()
    val screenStateFlow = _screenStateFlow.asSharedFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val searchTodoQuery: StateFlow<String> =
        _searchText
            .debounce(SEARCH_DELAY)
            .stateIn(viewModelScope, SharingStarted.Lazily, "")

    val todoList: StateFlow<List<ToDoTask>> =
        repository.getAllTasks()
            .combine(searchTodoQuery) { tasks, query ->
                _isSearching.value = false
                if (query.isEmpty()) {
                    tasks
                } else {
                    tasks.filter { it.taskName.contains(query, ignoreCase = true) }
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    fun onSearchTextChange(text: String) {
        _isSearching.value = true
        _searchText.update { text }
    }


    fun addToDoTask(taskName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (taskName.isNotEmpty()) {
                    _isLoading.emit(true)
                    repository.addTask(ToDoTask(taskName = taskName))
                    _screenStateFlow.emit(AddTaskScreenState.ResetErrorMessage(""))
                    delay(LOADING_DELAY)
                    _isLoading.emit(false)
                    _screenStateFlow.emit(AddTaskScreenState.GoBack)
                }
            } catch (e: TaskException) {
                _isLoading.emit(false)
                _screenStateFlow.emit(AddTaskScreenState.GoBackWithErrorMessage(e.message.toString()))
            } catch (e: Exception) {
                _isLoading.emit(false)
                _screenStateFlow.emit(AddTaskScreenState.GoBackWithErrorMessage("Something went wrong. Try again"))
            }
        }
    }

}