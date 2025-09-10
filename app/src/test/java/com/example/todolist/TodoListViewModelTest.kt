package com.example.todolist

import com.example.todolist.data.MockTodoRepository
import com.example.todolist.data.TodoListRepositoryImpl
import com.example.todolist.ui.TodoListViewModel
import org.junit.Test
import org.junit.Assert.*

class TodoListViewModelTest {
    val viewModel = TodoListViewModel(MockTodoRepository())

    @Test
    fun testCreateTodoItem() {
        var currentUiState = viewModel.uiState.value
        val expectedSize = currentUiState.todoList.size + 1

        viewModel.createTodo()

        currentUiState = viewModel.uiState.value
        val actualSize = currentUiState.todoList.size

        assertEquals(expectedSize, actualSize)
    }

    @Test
    fun testDeleteTodoItem() {
        viewModel.createTodo()
        var currentUiState = viewModel.uiState.value
        val expectedSize = currentUiState.todoList.size - 1

        viewModel.deleteTodo(currentUiState.todoList.keys.first())

        currentUiState = viewModel.uiState.value
        val actualSize = currentUiState.todoList.size

        assertEquals(expectedSize, actualSize)
    }
}