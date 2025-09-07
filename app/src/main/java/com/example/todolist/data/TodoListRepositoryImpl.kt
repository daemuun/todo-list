package com.example.todolist.data

import com.example.todolist.model.Todo

class TodoListRepositoryImpl: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return todoList 
    }

    override fun updateTodo(id: String, newTodo: Todo) {
        todoList.replace(id, newTodo)
    }

    override fun deleteTodo(id: String) {
        todoList.remove(id)
    }

    override fun createTodo(id: String, newTodo: Todo) {
        todoList.put(id, newTodo)
    }
}