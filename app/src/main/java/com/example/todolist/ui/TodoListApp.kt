package com.example.todolist.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.theme.TodoListTheme

object TodoListPath {
    const val START = "start"
    const val CHANGE_SCREEN = "todo/{id}"
}

@Composable
fun TodoListApp(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val currentUiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodoListPath.START,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(TodoListPath.START) {
                TodoListScreen(
                    todos = currentUiState.todoList,
                    onChangeTextClick = { id ->
                        navController.navigate("todo/$id")
                    },
                    onDeleteButtonClick = { id ->
                        viewModel.deleteTodo(id)
                    },
                    onAddButtonClick = {
                        viewModel.createTodo()
                    },
                    onChangeStatusButtonClick = { id ->
                        viewModel.changeTodoStatus(id)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(TodoListPath.CHANGE_SCREEN) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""

                ChangeTodoScreen(
                    todoId = id,
                    changedTodoTitleValue = "",
                    onTodoTitleChange = {},
                    onTodoTitleSaveClick = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoListAppPreview() {
    TodoListTheme {
        TodoListApp()
    }
}