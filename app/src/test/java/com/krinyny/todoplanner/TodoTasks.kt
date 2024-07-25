package com.krinyny.todoplanner

import com.krinyny.tododb.data.ToDoTask

object TodoTasks {

    fun getTasks() : List<ToDoTask> {
        var list = ArrayList<ToDoTask>()
        list.add(ToDoTask(id = 1, taskName = "First"))
        list.add(ToDoTask(id = 2, taskName = "Second"))
        list.add(ToDoTask(id = 3, taskName = "Third"))
        list.add(ToDoTask(id = 4, taskName = "Fourth"))
        return list
    }
}