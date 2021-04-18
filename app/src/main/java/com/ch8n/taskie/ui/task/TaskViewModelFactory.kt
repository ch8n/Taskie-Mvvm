package com.ch8n.taskie.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.taskie.data.repos.TaskRepo

class TaskViewModelFactory(
    private val taskRepo: TaskRepo
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepo) as T
    }
}