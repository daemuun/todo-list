package com.example.todolist.model

import androidx.annotation.StringRes
import com.example.todolist.data.SortCategory

data class FilterItem(
    @StringRes val sortName: Int,
    val sortCategory: SortCategory,
    val isSelected: Boolean = false,
    val reverseOrder: Boolean = false
)
