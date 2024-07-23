package com.krinyny.todoplanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krinyny.todoplanner.util.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskName: String,
)

class TaskException(message: String) : Exception(message)