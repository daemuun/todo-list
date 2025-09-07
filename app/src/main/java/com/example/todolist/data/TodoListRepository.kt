package com.example.todolist.data

import com.example.todolist.model.Todo

interface TodoListRepository {
    fun getAllTodo(): Map<String, Todo>
    fun updateTodo(id: String, newTodo: Todo)
    fun createTodo(id: String, newTodo: Todo)
    fun deleteTodo(id: String)
}