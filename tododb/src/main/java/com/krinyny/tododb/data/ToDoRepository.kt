package com.krinyny.tododb.data

import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getAllTasks(): Flow<List<ToDoTask>>

    suspend fun addTask(todoTask: ToDoTask)
}