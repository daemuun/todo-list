package com.example.todolist.data

import com.example.todolist.model.Todo

interface TodoListRepository {
    fun getAllTodo(): Map<String, Todo>
    fun createTodo(): String
    fun changeTodoStatus(id: String)
    fun changeTodoTitle(id: String, newTitle: String)
    fun deleteTodo(id: String)
}