package com.example.todolist.data

import com.example.todolist.model.Todo
import java.time.LocalDateTime
import java.util.UUID

class TodoListRepositoryImpl: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return todoList
    }

    override fun deleteTodo(id: String) {
        todoList.remove(id)
    }

    override fun createTodo() {
        todoList.put(generateId(), Todo())
    }

    override fun changeTodoStatus(id: String) {
        todoList[id]?.completed?.let { todoList[id]?.completed = !it }
        todoList[id]?.updatedAt = LocalDateTime.now()
    }

    override fun changeTodoTitle(id: String, newTitle: String) {
        todoList[id]?.title = newTitle
        todoList[id]?.updatedAt = LocalDateTime.now()
    }
}

private fun generateId() = UUID.randomUUID().toString()