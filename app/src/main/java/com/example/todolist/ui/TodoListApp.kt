package com.example.todolist.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun TodoListApp(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val currentUiState by viewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(currentUiState.todoList.values.toList()) { todoItem ->
            Text(todoItem.toString())
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