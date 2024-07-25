package com.krinyny.tododb.data

import com.krinyny.tododb.DatabaseConstants.ERROR_MESSAGE
import com.krinyny.tododb.DatabaseConstants.ERROR_TASK_NAME
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepositoryImpl @Inject constructor(private val todoDao: ToDoDao) : ToDoRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> = todoDao.getAllTasks()

    override suspend fun addTask(todoTask: ToDoTask) {
        if (todoTask.taskName.trim().equals(ERROR_TASK_NAME, ignoreCase = true)) {
            throw TaskException(ERROR_MESSAGE)
        } else {
            todoDao.addTask(toDoTask = todoTask)
        }

    }

}