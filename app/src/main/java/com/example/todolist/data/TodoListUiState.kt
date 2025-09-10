package com.example.todolist.data

import com.example.todolist.model.Todo

data class TodoListUiState(
    var todoList: Map<String, Todo>
)
