package com.example.todolist.data

import com.example.todolist.model.Todo
import java.time.LocalDateTime
import java.util.UUID

class TodoListRepositoryImpl: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return todoList.toMap()
    }

    override fun deleteTodo(id: String) {
        todoList.remove(id)
    }

    override fun createTodo(): String {
        val id = generateId()
        todoList[id] = Todo()
        return id
    }

    override fun changeTodoStatus(id: String) {
        todoList[id]?.let { currentTodo ->
            todoList[id] = currentTodo.copy(
                completed = !currentTodo.completed,
                updatedAt = LocalDateTime.now()
            )
        }
    }

    override fun changeTodoTitle(id: String, newTitle: String) {
        todoList[id]?.let { currentTodo ->
            todoList[id] = currentTodo.copy(
                title = newTitle,
                updatedAt = LocalDateTime.now()
            )
        }
    }
}

private fun generateId() = UUID.randomUUID().toString()