package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import com.example.todolist.data.TodoListRepository
import com.example.todolist.data.TodoListRepositoryImpl
import com.example.todolist.data.TodoListUiState
import com.example.todolist.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodoListViewModel(
    private val todoListRepository: TodoListRepository = TodoListRepositoryImpl()
): ViewModel() {
    private val _uiState = MutableStateFlow(TodoListUiState(todoListRepository.getAllTodo()))

    val uiState = _uiState.asStateFlow()

    fun createTodo() {
        val list = todoListRepository.createTodo()
        updateUiState(list)
    }

    fun deleteTodo(id: String) {
        val list = todoListRepository.deleteTodo(id)
        updateUiState(list)
    }

    fun changeTodoStatus(id: String) {
        val list = todoListRepository.changeTodoStatus(id)
        updateUiState(list)
    }

    fun changeTodoTitle(id: String, newTitle: String) {
        val list = todoListRepository.changeTodoTitle(id, newTitle)
        updateUiState(list)
    }

    private fun updateUiState(list: Map<String, Todo>) {
        _uiState.update { currentState ->
            currentState.copy(list.toMap())
        }
    }
}


