package com.example.todolist.data

import com.example.todolist.model.Todo
import java.time.LocalDateTime
import java.util.UUID

class MockTodoRepository: TodoListRepository {
    override fun getAllTodo(): Map<String, Todo> {
        return mockTodoList.toMap()
    }

    override fun deleteTodo(id: String) {
        mockTodoList.remove(id)
    }

    override fun createTodo(): String {
        val id = generateId()
        mockTodoList[id] = Todo()
        return id
    }

    override fun changeTodoStatus(id: String) {
        mockTodoList[id]?.let { currentTodo ->
            mockTodoList[id] = currentTodo.copy(
                completed = !currentTodo.completed,
                updatedAt = LocalDateTime.now()
            )
        }
    }

    override fun changeTodoTitle(id: String, newTitle: String) {
        mockTodoList[id]?.let { currentTodo ->
            mockTodoList[id] = currentTodo.copy(
                title = newTitle,
                updatedAt = LocalDateTime.now()
            )
        }
    }

    override fun getCompletedTodos(): Map<String, Todo> {
        return todoList.filter { (_, todo) -> todo.completed }.toMap()
    }

    override fun getUnCompletedTodos(): Map<String, Todo> {
        return todoList.filter { (_, todo) -> !todo.completed }.toMap()
    }
}

private fun generateId() = UUID.randomUUID().toString()