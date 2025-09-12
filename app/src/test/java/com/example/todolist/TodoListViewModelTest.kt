package com.example.todolist

import com.example.todolist.data.MockTodoRepository
import com.example.todolist.data.mockTodoList
import com.example.todolist.ui.TodoListViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.math.exp

class TodoListViewModelTest {
    private lateinit var viewModel: TodoListViewModel

    @Before
    fun setUp() {
        mockTodoList.clear()
        viewModel =  TodoListViewModel(MockTodoRepository())
    }

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

    @Test
    fun testChangeTodoStatusToDone() {
        viewModel.createTodo()
        var currentUiState = viewModel.uiState.value
        val todoId = currentUiState.todoList.keys.first()
        val expectedStatus = true

        viewModel.changeTodoStatus(todoId)

        currentUiState = viewModel.uiState.value
        val actualStatus = currentUiState.todoList[todoId]?.completed

        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun testChangeTodoStatusToNotDone() {
        viewModel.createTodo()
        var currentUiState = viewModel.uiState.value
        val todoId = currentUiState.todoList.keys.first()
        val expectedStatus = false

        viewModel.changeTodoStatus(todoId)
        viewModel.changeTodoStatus(todoId)

        currentUiState = viewModel.uiState.value
        val actualStatus = currentUiState.todoList[todoId]?.completed

        assertEquals(expectedStatus, actualStatus)
    }

    @Test
    fun testCreateManyTodo() {
        val expectedSize = 10000

        repeat(expectedSize) {
            viewModel.createTodo()
        }

        val actualSize = viewModel.uiState.value.todoList.size

        assertEquals(expectedSize, actualSize)
    }

    @Test
    fun testChangeTodoTitle() {
        viewModel.createTodo()
        var currentUiState = viewModel.uiState.value
        val expectedTitle = "create test"
        val todoId = currentUiState.todoList.keys.first()

        viewModel.changeTodoTitle(todoId, expectedTitle)

        currentUiState = viewModel.uiState.value
        val actualTitle = currentUiState.todoList[todoId]?.title

        assertEquals(expectedTitle, actualTitle)
    }

    @Test
    fun testChangeFiltersVisibility() {
        viewModel.changeFilterDialogVisibility()

        val currentUiState = viewModel.uiState.value
        val actualVisibility = currentUiState.showFilterDialog

        assertTrue(actualVisibility)
    }

    @Test
    fun testChangeNavigateTarget() {
        viewModel.createTodo()
        var currentUiState = viewModel.uiState.value

        val expectedNavigateTarget = currentUiState.todoList.keys.first()
        viewModel.changeNavigationTarget(expectedNavigateTarget)

        currentUiState = viewModel.uiState.value
        val actualNavigationTarget = currentUiState.navigateToTask

        assertEquals(expectedNavigateTarget, actualNavigationTarget)
    }

    @Test
    fun testUpdateChangedTitle() {
        val expectedTitleText = "hello world"
        viewModel.updateChangedTitle(expectedTitleText)
        val actualTitleText = viewModel.uiState.value.changedTitle

        assertEquals(expectedTitleText, actualTitleText)
    }
}