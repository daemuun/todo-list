package com.example.todolist.data

import com.example.todolist.model.Todo
import java.util.UUID

//mock todoList datasource
val todoList = mutableMapOf(
    UUID.randomUUID().toString() to Todo("Составить свой список задач")
)