package com.example.todolist.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
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
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_medium))
        ) {
            item {
                ManagmentState(
                    onFilterButtonClick = {},
                    onSearchButtonClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            items(
                items = todos.toList(),
                key = { it.first }
            ) { todo ->
                TodoItem(
                    todo = todo.second,
                    id = todo.first,
                    onChangeTextClick = onChangeTextClick,
                    onChangeStatusButtonClick = onChangeStatusButtonClick,
                    onDeleteButtonClick = onDeleteButtonClick,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }

        AddButton(
            onAddButtonClick = { onAddButtonClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_small)
                )
        )
    }
}

@Composable
fun ManagmentState(
    onFilterButtonClick: () -> Unit,
    onSearchButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ManagmentStateButton(
            onClick = onFilterButtonClick,
            imageVector = Icons.Filled.FilterAlt,
            stringId = R.string.filters_btn,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.weight(0.25f))

        ManagmentStateButton(
            onClick = onSearchButtonClick,
            imageVector = Icons.Filled.Search,
            stringId = R.string.search_btn,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ManagmentStateButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    @StringRes stringId: Int,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(stringId)
            )
            Text(
                text = stringResource(stringId),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}


@Composable
fun AddButton(
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onAddButtonClick() },
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
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompletionIndicator(completed = todo.completed)

        TodoCard(
            todo = todo,
            id = id,
            onDeleteButtonClick = onDeleteButtonClick,
            onChangeStatusButtonClick = onChangeStatusButtonClick,
            onChangeTextClick = onChangeTextClick
        )
    }
}

@Composable
fun TodoCard(
    todo: Todo,
    id: String,
    onDeleteButtonClick: (String) -> Unit,
    onChangeStatusButtonClick: (String) -> Unit,
    onChangeTextClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            DeleteButton(
                onDeleteButtonClick = onDeleteButtonClick,
                id = id
            )

            TodoText(
                onChangeTextClick = onChangeTextClick,
                title = todo.title,
                completed = todo.completed,
                id = id,
                modifier = Modifier.weight(1f)
            )

            StatusButton(
                onChangeStatusButtonClick = onChangeStatusButtonClick,
                id = id,
                completed = todo.completed
            )
        }
    }
}

@Composable
fun TodoText(
    onChangeTextClick: (String) -> Unit,
    title: String,
    completed: Boolean,
    id: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier
            .clickable { onChangeTextClick(id) }
            .padding(dimensionResource(R.dimen.padding_small)),
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

@Composable
fun DeleteButton(
    onDeleteButtonClick: (String) -> Unit,
    id: String,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onDeleteButtonClick(id) },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.delete_btn),
            tint = Color.Red
        )
    }
}

@Composable
fun StatusButton(
    onChangeStatusButtonClick: (String) -> Unit,
    id: String,
    completed: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onChangeStatusButtonClick(id) },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = if (completed) stringResource(R.string.no_done_btn) else stringResource(
                R.string.done_btn
            ),
            tint = if (completed) Color.Green else Color.DarkGray.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun CompletionIndicator(completed: Boolean, modifier: Modifier = Modifier) {
    Icon(
        imageVector = if (completed) {
            Icons.Filled.Circle
        } else {
            Icons.Outlined.Circle
        },
        contentDescription = null,
        modifier = modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
        tint = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun FilterDialog() {
    Dialog(
        onDismissRequest = {}
    ) { }
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
                "8" to Todo("Починить кран на кухне"),
                "9" to Todo("Сходить в спортзал"),
                "10" to Todo("Протестировать новое приложение"),
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