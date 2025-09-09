package com.example.todolist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.R
import com.example.todolist.model.Todo
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun TodoListScreen(
    todos: Map<String, Todo>,
    onChangeTextClick: (String) -> Unit,
    onChangeStatusButtonClick: (String) -> Unit,
    onDeleteButtonClick: (String) -> Unit,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(todos.toList()) { todo ->
            TodoItem(
                todo = todo.second,
                id = todo.first,
                onChangeTextClick = onChangeTextClick,
                onChangeStatusButtonClick = onChangeStatusButtonClick,
                onDeleteButtonClick = onDeleteButtonClick,
                modifier = Modifier.padding(12.dp)
            )
        }

        item {
            AddButton(
                onAddButtonClick = onAddButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}


@Composable
fun AddButton(
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onAddButtonClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.new_task_btn),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    id: String,
    onDeleteButtonClick: (String) -> Unit,
    onChangeStatusButtonClick: (String) -> Unit,
    onChangeTextClick: (String) -> Unit,
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
                contentDescription = stringResource(R.string.delete_btn),
                color = Color.Red,
                onActionButtonClick = onDeleteButtonClick,
                id = id
            )
            TodoTitle(
                title = todo.title,
                onChangeTextClick = onChangeTextClick,
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .shadow(1.dp, shape = MaterialTheme.shapes.small),
                id = id,
                completed = todo.completed
            )
            ActionButton(
                icon = Icons.Filled.Done,
                contentDescription = if (todo.completed) stringResource(R.string.no_done_btn) else stringResource(R.string.done_btn),
                color = Color.Green,
                onActionButtonClick = onChangeStatusButtonClick,
                id = id
            )
        }
    }
}

@Composable
fun TodoTitle(
    title: String,
    id: String,
    completed: Boolean,
    onChangeTextClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            text = title,
            modifier = Modifier
                .clickable(onClick = { onChangeTextClick(id) })
                .fillMaxWidth()
                .padding(all = 6.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = if (completed) {
                MaterialTheme.typography.titleMedium.copy(
                    textDecoration = TextDecoration.LineThrough
                )
            } else {
                MaterialTheme.typography.titleMedium
            }
        )
    }
}

@Composable
fun ActionButton(
    id: String,
    icon: ImageVector,
    contentDescription: String?,
    color: Color,
    onActionButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onActionButtonClick(id) },
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


@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    TodoListTheme {
        TodoListScreen(
            todos = mapOf(
                "1" to Todo("Подготовить отчет за квартал", completed = true),
                "2" to Todo("Забрать посылку с почты"),
                "3" to Todo("Записаться на курсы английского"),
                "4" to Todo("Купить подарок на день рождения"),
                "5" to Todo("Починить кран на кухне"),
                "6" to Todo("Сходить в спортзал"),
                "7" to Todo("Протестировать новое приложение"),
            ),
            onAddButtonClick = {},
            onChangeStatusButtonClick = {},
            onChangeTextClick = {},
            onDeleteButtonClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun TodoItemPrewiew() {
    TodoListTheme {
        TodoItem(
            todo = Todo("Подготовить отчет за квартал"),
            onChangeTextClick = {},
            onChangeStatusButtonClick = {},
            onDeleteButtonClick = {},
            id = "1"
        )
    }
}