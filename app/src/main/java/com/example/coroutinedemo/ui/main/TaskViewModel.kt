
package com.example.coroutinedemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinedemo.data.Task
import com.example.coroutinedemo.data.TaskRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())

    val uiState: StateFlow<TasksUiState> =
        _uiState.asStateFlow()

    private var loadJob: Job? = null

    fun loadData(shouldFail: Boolean = false) {

        loadJob?.cancel()

        loadJob = viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                coroutineScope {

                    val tasks = async {
                        repository.getTasks(shouldFail)
                    }

                    val announcement = async {
                        repository.getAnnouncement()
                    }

                    val courseCount = async {
                        repository.getCourseCount()
                    }

                    val loadedTasks = tasks.await()
                    val loadedAnnouncement = announcement.await()
                    val loadedCourseCount = courseCount.await()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            tasks = loadedTasks,
                            announcement = loadedAnnouncement,
                            courseCount = loadedCourseCount
                        )
                    }
                }

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to load: ${e.message}"
                    )
                }
            }
        }
    }

    fun cancelLoading() {
        loadJob?.cancel()

        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = "Loading cancelled"
            )
        }
    }

    fun onTap() {
        _uiState.update {
            it.copy(tapCount = it.tapCount + 1)
        }
    }

    fun blockMainThread() {
        Thread.sleep(3000L)
    }

    fun addTask(title: String, course: String) {

        if (title.isBlank() || course.isBlank()) {
            return
        }

        val newId = (_uiState.value.tasks.maxOfOrNull { it.id } ?: 0) + 1

        val newTask = Task(
            id = newId,
            title = title.trim(),
            course = course.trim()
        )

        _uiState.update {
            it.copy(tasks = it.tasks + newTask)
        }
    }

    fun clearTasks() {
        _uiState.update {
            it.copy(tasks = emptyList())
        }
    }

    fun sortTasks() {

        viewModelScope.launch {

            try {
                val sortedTasks = repository.sortTasks(
                    _uiState.value.tasks
                )

                _uiState.update {
                    it.copy(tasks = sortedTasks)
                }

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Sort failed: ${e.message}")
                }
            }
        }
    }
}
