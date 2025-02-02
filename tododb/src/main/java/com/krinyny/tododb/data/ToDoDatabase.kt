package com.krinyny.tododb.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

}