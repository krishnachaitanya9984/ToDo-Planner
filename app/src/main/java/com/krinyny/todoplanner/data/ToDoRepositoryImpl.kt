package com.krinyny.todoplanner.data

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepositoryImpl @Inject constructor(private val todoDao: ToDoDao) : ToDoRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> = todoDao.getAllTasks()

    override suspend fun addTask(todoTask: ToDoTask) {
        if (todoTask.taskName.trim().equals("Error", ignoreCase = true)) {
            throw TaskException("Failed to add TODO")
        } else {
            todoDao.addTask(toDoTask = todoTask)
        }

    }

    override fun searchTasks(searchStr: String) = todoDao.searchItems(searchStr)


}