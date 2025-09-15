package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import com.example.todolist.data.SortCategory
import com.example.todolist.data.TodoListRepository
import com.example.todolist.data.TodoListRepositoryImpl
import com.example.todolist.data.TodoListUiState
import com.example.todolist.model.SortItem
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
        getSortedAndFilteredTodo()
    }

    fun changeTodoStatus(id: String) {
        todoListRepository.changeTodoStatus(id)
        getSortedAndFilteredTodo()
    }

    fun changeTodoTitle(id: String, newTitle: String) {
        todoListRepository.changeTodoTitle(id, newTitle)
        getSortedAndFilteredTodo()
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

    fun changeSearchDialogVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                showSearchDialog = !currentState.showSearchDialog
            )
        }
    }

    fun getSortedAndFilteredTodo() {
        val filteredList = when (_uiState.value.isCompletedFilter) {
            true -> todoListRepository.getCompletedTodos()
            false -> todoListRepository.getUnCompletedTodos()
            null -> todoListRepository.getAllTodo()
        }

        val sortItem = _uiState.value.selectedSortItem
        val sortedTodoList = if (sortItem != null) {
            when (sortItem.sortCategory) {
                SortCategory.Alphabetical -> {
                    if (sortItem.reverseOrder) {
                        filteredList.entries.sortedByDescending { it.value.title }
                    } else {
                        filteredList.entries.sortedBy { it.value.title }
                    }
                }
                SortCategory.CreatedAt -> {
                    if (sortItem.reverseOrder) {
                        filteredList.entries.sortedByDescending { it.value.createdAt }
                    } else {
                        filteredList.entries.sortedBy { it.value.createdAt }
                    }
                }
                SortCategory.UpdatedAt -> {
                    if (sortItem.reverseOrder) {
                        filteredList.entries.sortedByDescending { it.value.updatedAt }
                    } else {
                        filteredList.entries.sortedBy { it.value.updatedAt }
                    }
                }
                SortCategory.TodoLength -> {
                    if (sortItem.reverseOrder) {
                        filteredList.entries.sortedByDescending { it.value.title.length }
                    } else {
                        filteredList.entries.sortedBy { it.value.title.length }
                    }
                }
            }.associate { it.key to it.value }
        } else {
            filteredList
        }

        _uiState.update { currentState ->
            currentState.copy(
                todoList = sortedTodoList,
                showFilterDialog = false
            )
        }
    }

    fun clearSortsAndFilters() {
        _uiState.update {
            it.copy(
                showFilterDialog = false,
                selectedSortItem = null,
                isCompletedFilter = null
            )
        }
    }

    fun changeFilterOrder() {
        _uiState.update { currentState ->
            currentState.selectedSortItem?.let { sortItem ->
                currentState.copy(
                    selectedSortItem = sortItem.copy(reverseOrder = !sortItem.reverseOrder)
                )
            } ?: currentState
        }
    }

    fun changeSelectedSortItem(sortItem: SortItem) {
        _uiState.update { currentUiState ->
            currentUiState.copy(selectedSortItem = sortItem)
        }
    }

    fun changeIsCompletedFilter(filter: Boolean?) {
        _uiState.update { currentUiState ->
            currentUiState.copy(isCompletedFilter = filter)
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
                selectedSortItem = null,
                isCompletedFilter = null
            )
        }
    }
}