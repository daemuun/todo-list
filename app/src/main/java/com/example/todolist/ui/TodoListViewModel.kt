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
) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListUiState(emptyMap()))

    val uiState = _uiState.asStateFlow()

    init {
        setupState()
    }

    fun createTodo() {
        val id = todoListRepository.createTodo()
        _uiState.update { currentState ->
            currentState.copy(
                todoList = todoListRepository.getAllTodo(),
                navigateToTask = id
            )
        }
    }

    fun changeNavigationTarget(id: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                navigateToTask = id
            )
        }
    }

    fun deleteTodo(id: String) {
        todoListRepository.deleteTodo(id)
        updateUiState(list = todoListRepository.getAllTodo())
    }

    fun changeTodoStatus(id: String) {
        todoListRepository.changeTodoStatus(id)
        updateUiState(list = todoListRepository.getAllTodo())
    }

    fun changeTodoTitle(id: String, newTitle: String) {
        todoListRepository.changeTodoTitle(id, newTitle)
        updateUiState(list = todoListRepository.getAllTodo())
    }

    fun updateChangedTitle(newTitle: String) {
        _uiState.update { currentState ->
            currentState.copy(changedTitle = newTitle)
        }
    }

    fun changeFilterDialogVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                showFilterDialog = !currentState.showFilterDialog
            )
        }
    }

    fun changeSearchDialogVisibility()  {
        _uiState.update { currentState ->
            currentState.copy(
                showSearchDialog = !currentState.showSearchDialog
            )
        }
    }

    private fun updateUiState(list: Map<String, Todo>) {
        _uiState.update { currentState ->
            currentState.copy(list)
        }
    }

    private fun setupState() {
        _uiState.update { currentState ->
            currentState.copy(
                todoList = todoListRepository.getAllTodo(),
                changedTitle = "",
                navigateToTask = null,
                showFilterDialog = false,
                showSearchDialog = false,
            )
        }
    }
}


