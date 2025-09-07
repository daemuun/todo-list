package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import com.example.todolist.data.TodoListRepository
import com.example.todolist.data.TodoListRepositoryImpl
import com.example.todolist.data.TodoListUiState
import com.example.todolist.data.todoList
import com.example.todolist.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class TodoListViewModel(private val todoListRepository: TodoListRepository = TodoListRepositoryImpl()): ViewModel() {
    private val allTodo: Map<String, Todo> = todoListRepository.getAllTodo()
    private val _uiState = MutableStateFlow(TodoListUiState(allTodo))

    val uiState = _uiState.asStateFlow()

    fun createTodo(newTodo: Todo) {
        val randId: String = generateId()
        todoListRepository.createTodo(randId, newTodo)
        updateUiState()
    }

    fun updateTodo(id: String, newTodo: Todo) {
        todoListRepository.updateTodo(id, newTodo)
        updateUiState()
    }

    fun deleteTodo(id: String) {
        todoListRepository.deleteTodo(id)
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.update { currentState ->
            currentState.copy(todoListRepository.getAllTodo())
        }
    }

    private fun generateId() = UUID.randomUUID().toString()
}


