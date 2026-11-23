package com.example.coroutinedemo.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coroutinedemo.ui.theme.SageBackground
import com.example.coroutinedemo.ui.theme.SageCard
import com.example.coroutinedemo.ui.theme.SageDark
import com.example.coroutinedemo.ui.theme.SageError

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    TasksContent(
        state = state,
        onLoad = { viewModel.loadData() },
        onLoadWithError = {
            viewModel.loadData(shouldFail = true)
        },
        onCancel = viewModel::cancelLoading,
        onTap = viewModel::onTap,
        onBlock = viewModel::blockMainThread,
        onRetry = { viewModel.loadData() },
        onSort = viewModel::sortTasks,
        onAddTask = viewModel::addTask,
        onClear = viewModel::clearTasks,
        modifier = modifier
    )
}

@Composable
fun TasksContent(
    state: TasksUiState,
    onLoad: () -> Unit,
    onLoadWithError: () -> Unit,
    onCancel: () -> Unit,
    onTap: () -> Unit,
    onBlock: () -> Unit,
    onRetry: () -> Unit,
    onSort: () -> Unit,
    onAddTask: (String, String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    var titleInput by remember { mutableStateOf("") }
    var courseInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SageBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Student Task Manager",
            style = MaterialTheme.typography.headlineSmall,
            color = SageDark
        )

        Text(
            text = "Manage your study activities",
            style = MaterialTheme.typography.bodyMedium,
            color = SageDark
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onLoad,
                enabled = !state.isLoading
            ) {
                Text("Load")
            }

            OutlinedButton(
                onClick = onLoadWithError,
                enabled = !state.isLoading
            ) {
                Text("Load (fail)")
            }

            OutlinedButton(
                onClick = onCancel,
                enabled = state.isLoading
            ) {
                Text("Cancel")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onTap) {
                Text("Tap me: ${state.tapCount}")
            }

            OutlinedButton(onClick = onBlock) {
                Text("Block UI")
            }
        }

        if (state.isLoading) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(4.dp)
                )

                Text(
                    text = "Loading data...",
                    color = SageDark
                )
            }
        }

        if (state.errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = state.errorMessage,
                        color = SageError
                    )

                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }
        }

        if (state.announcement.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = SageCard
                )
            ) {
                Text(
                    text = "Announcement: ${state.announcement}",
                    modifier = Modifier.padding(12.dp),
                    color = SageDark
                )
            }
        }

        if (state.courseCount > 0) {
            Text(
                text = "Total Courses: ${state.courseCount}",
                color = SageDark,
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Form tambah tugas
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = SageCard
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Add New Task",
                    style = MaterialTheme.typography.titleMedium,
                    color = SageDark
                )

                OutlinedTextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    label = { Text("Task title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = courseInput,
                    onValueChange = { courseInput = it },
                    label = { Text("Course name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        onAddTask(titleInput, courseInput)
                        titleInput = ""
                        courseInput = ""
                    },
                    enabled = titleInput.isNotBlank() &&
                            courseInput.isNotBlank()
                ) {
                    Text("Add Task")
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Task List (${state.tasks.size})",
                style = MaterialTheme.typography.titleLarge,
                color = SageDark
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedButton(onClick = onSort) {
                    Text("Sort")
                }

                OutlinedButton(
                    onClick = onClear,
                    enabled = state.tasks.isNotEmpty()
                ) {
                    Text("Clear")
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                state.tasks,
                key = { it.id }
            ) { task ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SageCard
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = SageDark
                        )

                        Text(
                            text = task.course,
                            style = MaterialTheme.typography.bodySmall,
                            color = SageDark
                        )
                    }
                }
            }
        }
    }
}
