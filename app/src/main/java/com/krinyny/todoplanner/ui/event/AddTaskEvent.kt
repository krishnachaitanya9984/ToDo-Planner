package com.krinyny.todoplanner.ui.event

sealed class AddTaskEvent{
    data class AddToDoTask(val taskName: String): AddTaskEvent()
}