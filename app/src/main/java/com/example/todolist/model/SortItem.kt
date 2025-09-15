package com.example.todolist.model

import androidx.annotation.StringRes
import com.example.todolist.data.SortCategory

data class SortItem(
    @StringRes val sortName: Int,
    val sortCategory: SortCategory,
    val reverseOrder: Boolean = false
)
