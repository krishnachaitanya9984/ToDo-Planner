package com.krinyny.todoplanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krinyny.todoplanner.util.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskName: String,
)