package com.krinyny.todoplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krinyny.tododb.data.TaskException
import com.krinyny.tododb.data.ToDoRepositoryImpl
import com.krinyny.tododb.data.ToDoTask
import com.krinyny.todoplanner.ui.event.AddTaskEvent
import com.krinyny.todoplanner.ui.state.AddTaskScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel @Inject constructor(
    private val repository: ToDoRepositoryImpl
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _screenStateFlow = MutableSharedFlow<AddTaskScreenState>()
    val screenStateFlow = _screenStateFlow.asSharedFlow()

    fun onUIEvent(event: AddTaskEvent) {
        when (event) {
            is AddTaskEvent.AddToDoTask -> addToDoTask(event.taskName)
        }
    }

    private fun addToDoTask(taskName : String) {
        viewModelScope.launch {
            try {
                if (taskName.isNotEmpty()) {
                    _isLoading.emit(true)
                    repository.addTask(ToDoTask(taskName = taskName))
                    delay(3000)
                    _screenStateFlow.emit(AddTaskScreenState.GoBack)
                    _isLoading.emit(false)
                }
            } catch (e: TaskException) {
                _screenStateFlow.emit(AddTaskScreenState.GoBackWithErrorMessage("Failed To Add TODO"))
            } catch (e: Exception) {
                _screenStateFlow.emit(AddTaskScreenState.GoBackWithErrorMessage("Something went wrong. Try again"))
            }
        }
    }
}