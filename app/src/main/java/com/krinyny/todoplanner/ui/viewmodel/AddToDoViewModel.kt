package com.krinyny.todoplanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krinyny.todoplanner.data.ToDoRepositoryImpl
import com.krinyny.todoplanner.data.ToDoTask
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
            is AddTaskEvent.AddToDoTask -> {
                viewModelScope.launch {
                    val taskName = event.taskName
                    try {
                        if (taskName.isNotEmpty()) {
                            repository.addTask(ToDoTask(taskName = taskName))
                            _isLoading.emit(true)
                            delay(3000)
                            _screenStateFlow.emit(AddTaskScreenState.GoBack)
                            _isLoading.emit(false)
                        }
                    } catch (e: Exception) {
                        _screenStateFlow.emit(AddTaskScreenState.GoBackWithErrorMessage("Failed To Add TODO"))
                    }
                }
            }
        }
    }
}