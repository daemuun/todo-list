package com.example.todolist.data

import com.example.todolist.model.Todo
import java.time.LocalDateTime
import java.util.UUID

class TodoListRepositoryImpl: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return todoList.toMap()
    }

    override fun deleteTodo(id: String): Map<String, Todo> {
        todoList.remove(id)
        return todoList.toMap()
    }

    override fun createTodo(): Map<String, Todo> {
        val id = generateId()
        todoList[id] = Todo()
        return todoList.toMap()
    }

    override fun changeTodoStatus(id: String): Map<String, Todo> {
        todoList[id]?.let { currentTodo ->
            todoList[id] = currentTodo.copy(
                completed = !currentTodo.completed,
                updatedAt = LocalDateTime.now()
            )
        }
        return todoList.toMap()
    }

    override fun changeTodoTitle(id: String, newTitle: String): Map<String, Todo> {
        todoList[id]?.let { currentTodo ->
            todoList[id] = currentTodo.copy(
                title = newTitle,
                updatedAt = LocalDateTime.now()
            )
        }
        return todoList.toMap()
    }
}

private fun generateId() = UUID.randomUUID().toString()