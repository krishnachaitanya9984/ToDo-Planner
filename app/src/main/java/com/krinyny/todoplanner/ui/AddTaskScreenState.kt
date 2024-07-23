package com.krinyny.todoplanner.ui


sealed class AddTaskScreenState {
    data object GoBack: AddTaskScreenState()
    data class GoBackWithErrorMessage(val errorMessage: String): AddTaskScreenState()
}