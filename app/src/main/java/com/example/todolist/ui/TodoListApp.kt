package com.example.todolist.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.R
import com.example.todolist.data.Sorts
import com.example.todolist.model.Todo
import com.example.todolist.ui.theme.TodoListTheme

object TodoListPath {
    const val START = "start"
    const val CHANGE_SCREEN = "todo/{id}"
    const val CHANGE_SCREEN_BASE = "todo/"
}

fun shareTodoList(context: Context, todoList: Map<String, Todo>) {
    val shareText = buildString {
        append("📋 Мой список задач\n\n")

        todoList.values.forEachIndexed { index, todo ->
            val status = if (todo.completed) "✅" else "⏳"
            append("${index + 1}. $status ${todo.title}\n")
        }

        val completedCount = todoList.values.count { it.completed }
        val totalCount = todoList.size
        append("\nПрогресс: $completedCount/$totalCount выполненно")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Мой список задач")
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    val shareIntent = Intent.createChooser(intent, "Поделиться списком задач")
    context.startActivity(shareIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListTopAppBar(
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            navigationIcon = {
                FilledTonalIconButton(
                    onClick = navigateHome,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = stringResource(R.string.back_top_btn)
                    )
                }
            },
            actions = {
                FilledTonalIconButton(
                    onClick = onShareClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(R.string.share_todos)
                    )
                }
            },
        )
        HorizontalDivider()
    }
}


@Composable
fun TodoListApp(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val currentUiState by viewModel.uiState.collectAsState()
    val localContext = LocalContext.current

    LaunchedEffect(currentUiState.navigateToTask) {
        currentUiState.navigateToTask?.let { id ->
            navController.navigate("${TodoListPath.CHANGE_SCREEN_BASE}$id")
            viewModel.updateChangedTitle(currentUiState.todoList[id]?.title ?: "")
            viewModel.changeNavigationTarget(null)
        }
    }

    Scaffold(
        topBar = {
            TodoListTopAppBar(
                navigateHome = {
                    navController.popBackStack(
                        route = TodoListPath.START,
                        inclusive = false
                    )
                },
                onShareClick = {
                    shareTodoList(
                        context = localContext,
                        todoList = currentUiState.todoList
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodoListPath.START,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(TodoListPath.START) {
                TodoListScreen(
                    todos = currentUiState.todoList,
                    sorts = Sorts,
                    onChangeTextClick = { id ->
                        viewModel.changeNavigationTarget(id)
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
                    onSearchClick = {
                        viewModel.changeSearchDialogVisibility()
                    },
                    onFiltersClick = {
                        viewModel.changeFilterDialogVisibility()
                    },
                    onFiltersDismiss = {
                        viewModel.changeFilterDialogVisibility()
                    },
                    onSearchDismiss = {
                        viewModel.changeSearchDialogVisibility()
                    },
                    isCompletedFilter = currentUiState.isCompletedFilter,
                    onSortSelected = { sortItem ->
                        viewModel.changeSelectedSortItem(sortItem)
                    },
                    onFilterSelected = { filter ->
                        viewModel.changeIsCompletedFilter(filter)
                    },
                    onFilterAndSortAccept = {
                        viewModel.getSortedAndFilteredTodo()
                    },
                    onFilterAndSortCancel = {
                        viewModel.clearSortsAndFilters()
                    },
                    onSortOrderChange = {
                        viewModel.changeFilterOrder()
                    },
                    selectedSortItem = currentUiState.selectedSortItem,
                    showSearch = currentUiState.showSearchDialog,
                    showFilters = currentUiState.showFilterDialog,
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(TodoListPath.CHANGE_SCREEN) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""

                ChangeTodoScreen(
                    todoId = id,
                    todoTitle = currentUiState.changedTitle,
                    onTodoTitleChange = { newTitle ->
                        viewModel.updateChangedTitle(newTitle)
                    },
                    onTodoTitleSaveClick = { id ->
                        viewModel.changeTodoTitle(id, currentUiState.changedTitle)
                        viewModel.updateChangedTitle("")
                        navController.navigate(TodoListPath.START)
                    },
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