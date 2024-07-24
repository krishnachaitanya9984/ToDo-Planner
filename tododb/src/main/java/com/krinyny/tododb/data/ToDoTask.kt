package com.krinyny.tododb.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krinyny.tododb.DatabaseConstants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskName: String,
)

class TaskException(message: String) : Exception(message)