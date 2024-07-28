package com.krinyny.tododb.data

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepositoryImpl @Inject constructor(private val todoDao: ToDoDao) : ToDoRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> = todoDao.getAllTasks()

    override suspend fun addTask(todoTask: ToDoTask) {
        todoDao.addTask(toDoTask = todoTask)
    }

}