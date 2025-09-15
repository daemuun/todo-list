package com.example.todolist.model

import java.time.LocalDateTime

data class Todo(
    var title: String = "",
    var completed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
