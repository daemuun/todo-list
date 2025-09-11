package com.example.todolist.ui

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
        OutlinedButton(
            onClick = onFilterButtonClick,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterAlt,
                    contentDescription = stringResource(R.string.filters_btn)
                )
                Text(
                    text = stringResource(R.string.filters_btn),
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
        OutlinedButton(
            onClick = onSearchButtonClick,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_btn)
                )
                Text(
                    text = stringResource(R.string.search_btn),
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
            }
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
        Icon(
            imageVector = if (todo.completed) {
                Icons.Filled.Circle
            } else (Icons.Outlined.Circle),
            contentDescription = null,
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium)),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        OutlinedCard {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                IconButton(
                    onClick = { onDeleteButtonClick(id) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.delete_btn),
                        tint = Color.Red
                    )
                }

                Text(
                    text = todo.title,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onChangeTextClick(id) }
                        .padding(dimensionResource(R.dimen.padding_small)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = if (todo.completed) {
                        MaterialTheme.typography.titleMedium.copy(
                            textDecoration = TextDecoration.LineThrough
                        )
                    } else {
                        MaterialTheme.typography.titleMedium
                    }
                )

                IconButton(
                    onClick = { onChangeStatusButtonClick(id) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = if (todo.completed) stringResource(R.string.no_done_btn) else stringResource(
                            R.string.done_btn
                        ),
                        tint = if (todo.completed) Color.Green else Color.DarkGray.copy(alpha = 0.5f)
                    )
                }
            }
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