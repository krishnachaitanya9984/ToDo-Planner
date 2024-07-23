package com.krinyny.todoplanner.ui.state


sealed class AddTaskScreenState {
    data object GoBack : AddTaskScreenState()
    data class GoBackWithErrorMessage(val errorMessage: String) : AddTaskScreenState()
}