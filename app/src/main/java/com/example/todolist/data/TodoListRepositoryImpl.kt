package com.example.todolist.data

import com.example.todolist.model.Todo
import java.time.LocalDateTime
import java.util.UUID

class TodoListRepositoryImpl: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return todoList
    }

    override fun deleteTodo(id: String): Map<String, Todo> {
        todoList.remove(id)
        return todoList
    }

    override fun createTodo(): Map<String, Todo> {
        todoList.put(generateId(), Todo())
        return todoList
    }

    override fun changeTodoStatus(id: String): Map<String, Todo> {
        todoList[id]?.completed?.let { todoList[id]?.completed = !it }
        todoList[id]?.updatedAt = LocalDateTime.now()
        return todoList
    }

    override fun changeTodoTitle(id: String, newTitle: String): Map<String, Todo> {
        todoList[id]?.title = newTitle
        todoList[id]?.updatedAt = LocalDateTime.now()
        return todoList
    }
}

private fun generateId() = UUID.randomUUID().toString()