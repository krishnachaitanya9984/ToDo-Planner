package com.krinyny.todoplanner.ui.event

sealed class TodoListEvent{
    data object GetAllTasks : TodoListEvent()
    data class SearchTasks(val searchString: String) : TodoListEvent()
}