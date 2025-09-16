package com.example.todolist.data

import com.example.todolist.model.SortItem
import com.example.todolist.model.Todo

data class TodoListUiState(
    val todoList: Map<String, Todo>,
    val changedTitle: String = "",
    val navigateToTask: String? = null,
    val showFilterDialog: Boolean = false,
    val showSearchDialog: Boolean = false,
    val selectedSortItem: SortItem? = null,
    val isCompletedFilter: Boolean? = null,
    val searchQuery: String = ""
)
