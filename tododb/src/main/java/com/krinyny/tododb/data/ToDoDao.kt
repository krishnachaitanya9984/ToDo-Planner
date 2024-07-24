package com.krinyny.tododb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_planner_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(toDoTask: ToDoTask)

    @Query("SELECT * FROM todo_planner_table WHERE taskName LIKE '%' || :search || '%'")
    fun searchItems(search: String): Flow<List<ToDoTask>>
}