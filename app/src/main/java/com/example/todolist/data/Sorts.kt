package com.example.todolist.data

import com.example.todolist.R
import com.example.todolist.model.SortItem

val Sorts = listOf(
    SortItem(
        sortName = R.string.alphabet_sort,
        sortCategory = SortCategory.Alphabetical,
    ),
    SortItem(
        sortName = R.string.created_sort,
        sortCategory = SortCategory.CreatedAt
    ),
    SortItem(
        sortName = R.string.updated_sort,
        sortCategory = SortCategory.UpdatedAt
    ),
    SortItem(
        sortName = R.string.length_sort,
        sortCategory = SortCategory.TodoLength
    )
)