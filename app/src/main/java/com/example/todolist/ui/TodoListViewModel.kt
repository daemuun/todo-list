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

class TodoListViewModel(
    private val todoListRepository: TodoListRepository = TodoListRepositoryImpl()
): ViewModel() {
    private val _uiState = MutableStateFlow(TodoListUiState(todoListRepository.getAllTodo()))

    val uiState = _uiState.asStateFlow()

    fun createTodo() {
        todoListRepository.createTodo()
        updateUiState()
    }

    fun deleteTodo(id: String) {
        todoListRepository.deleteTodo(id)
        updateUiState()
    }

    fun changeTodoStatus(id: String) {
        todoListRepository.changeTodoStatus(id)
        updateUiState()
    }

    fun changeTodoTitle(id: String, newTitle: String) {
        todoListRepository.changeTodoTitle(id, newTitle)
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.update { currentState ->
            currentState.copy(todoListRepository.getAllTodo())
        }
    }
}


