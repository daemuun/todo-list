package com.example.todolist.model

import java.time.LocalDateTime

data class Todo(
    val title: String = "",
    val completed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
