package com.example.todolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist.R
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun ChangeTodoScreen(
    todoId: String,
    changedTodoTitleValue: String,
    onTodoTitleChange: (String) -> Unit,
    onTodoTitleSaveClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val paddingMedium = dimensionResource(R.dimen.padding_medium)

    Box(modifier = modifier) {
        TextField(
            value = changedTodoTitleValue,
            onValueChange = { onTodoTitleChange(it) },
            modifier = Modifier
                .fillMaxSize(1f)
        )
        Button(
            onClick = { onTodoTitleSaveClick(todoId) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(paddingMedium)
        ) {
            Text(
                text = stringResource(R.string.save_btn),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = paddingSmall, horizontal = paddingMedium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangeTodoScreenPreview() {
    TodoListTheme {
        ChangeTodoScreen(
            changedTodoTitleValue = "",
            onTodoTitleChange = {},
            onTodoTitleSaveClick = {},
            todoId = "1",
            modifier = Modifier.fillMaxSize()
        )
    }
}