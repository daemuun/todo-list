package com.example.todolist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.model.Todo
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun TodoListScreen(todos: List<Todo>, modifier: Modifier = Modifier) {

}

@Composable
fun TodoItem(
    todo: Todo,
    onDeleteButtonClick: () -> Unit,
    onDoneButtonClick: () -> Unit,
    onChangeTextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionButton(
                icon = Icons.Filled.Close,
                contentDescription = "",
                color = Color.Red,
                onActionButtonClick = onDeleteButtonClick
            )
            Text(
                text = todo.title,
                modifier = Modifier.clickable(onClick = onChangeTextClick)
            )
            ActionButton(
                icon = Icons.Filled.Done,
                contentDescription = "",
                color = Color.Green,
                onActionButtonClick = onDoneButtonClick
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    contentDescription: String?,
    color: Color,
    onActionButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onActionButtonClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(color)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}


@Preview
@Composable
fun TodoListScreenPrewiew() {
    TodoListTheme {
        TodoListScreen(todos = listOf<Todo>())
    }
}

@Preview
@Composable
fun TodoItemPrewiew() {
    TodoListTheme {
        TodoItem(
            todo = Todo("asdasdasf"),
            onChangeTextClick = {},
            onDoneButtonClick = {},
            onDeleteButtonClick = {}
        )
    }
}