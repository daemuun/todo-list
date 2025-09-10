package com.example.todolist.data

import com.example.todolist.model.Todo
import java.time.LocalDateTime
import java.util.UUID

class MockTodoRepository: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return mockTodoList
    }

    override fun deleteTodo(id: String): Map<String, Todo> {
        mockTodoList.remove(id)
        return mockTodoList
    }

    override fun createTodo(): Map<String, Todo> {
        mockTodoList.put(generateId(), Todo())
        return mockTodoList
    }

    override fun changeTodoStatus(id: String): Map<String, Todo> {
        mockTodoList[id]?.completed?.let { mockTodoList[id]?.completed = !it }
        mockTodoList[id]?.updatedAt = LocalDateTime.now()
        return mockTodoList
    }

    override fun changeTodoTitle(id: String, newTitle: String): Map<String, Todo> {
        mockTodoList[id]?.title = newTitle
        mockTodoList[id]?.updatedAt = LocalDateTime.now()
        return mockTodoList
    }
}

private fun generateId() = UUID.randomUUID().toString()