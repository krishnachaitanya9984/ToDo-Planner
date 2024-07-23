package com.krinyny.todoplanner.data

import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getAllTasks(): Flow<List<ToDoTask>>

    suspend fun addTask(todoTask: ToDoTask)

    fun searchTasks(searchStr: String): Flow<List<ToDoTask>>
}