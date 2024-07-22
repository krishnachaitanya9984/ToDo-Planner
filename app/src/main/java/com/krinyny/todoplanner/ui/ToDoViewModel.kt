package com.krinyny.todoplanner.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.krinyny.todoplanner.data.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {
    private val _todoContent = mutableStateOf("")
    val todoContent: State<String> = _todoContent

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
}