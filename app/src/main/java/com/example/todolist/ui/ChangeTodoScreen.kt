package com.example.todolist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist.R
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun ChangeTodoScreen(
    todoId: String,
    todoTitle: String,
    onTodoTitleChange: (String) -> Unit,
    onTodoTitleSaveClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val paddingMedium = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = todoTitle,
            onValueChange = { onTodoTitleChange(it) },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(
                    top = paddingMedium,
                    start = paddingMedium,
                    end = paddingMedium
                ),
            label = { Text(text = stringResource(R.string.change_title_label)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            shape = MaterialTheme.shapes.large
        )
        OutlinedButton(
            onClick = { onTodoTitleSaveClick(todoId) },
            modifier = Modifier
                .padding(paddingMedium)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.save_btn),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangeTodoScreenPreview() {
    TodoListTheme {
        ChangeTodoScreen(
            todoTitle = "",
            onTodoTitleChange = {},
            onTodoTitleSaveClick = {},
            todoId = "1",
            modifier = Modifier.fillMaxSize()
        )
    }
}