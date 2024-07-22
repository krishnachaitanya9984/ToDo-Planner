package com.krinyny.todoplanner.data

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val todoDao: ToDoDao) {

    val getAllTasks: Flow<List<ToDoTask>> = todoDao.getAllTasks()

    suspend fun addTask(todoTask: ToDoTask) {
        todoDao.addTask(toDoTask = todoTask)
    }

}