package com.example.todolist.data

import com.example.todolist.model.Todo

interface TodoListRepository {
    fun getAllTodo(): Map<String, Todo>
    fun createTodo(): Map<String, Todo>
    fun changeTodoStatus(id: String): Map<String, Todo>
    fun changeTodoTitle(id: String, newTitle: String): Map<String, Todo>
    fun deleteTodo(id: String): Map<String, Todo>
}