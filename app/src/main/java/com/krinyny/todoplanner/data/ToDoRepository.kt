package com.krinyny.todoplanner.data

import android.icu.text.StringSearch
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val todoDao: ToDoDao) {

    val getAllTasks: Flow<List<ToDoTask>> = todoDao.getAllTasks()

    suspend fun addTask(todoTask: ToDoTask) {
        if(todoTask.taskName.trim().equals("Error", ignoreCase = true)) {
            throw TaskException("Failed to add TODO")
        } else {
            todoDao.addTask(toDoTask = todoTask)
        }

    }

    suspend fun searchTasks(searchStr: String) = todoDao.searchItems(searchStr)


}