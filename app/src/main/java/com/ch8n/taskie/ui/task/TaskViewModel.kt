package com.ch8n.taskie.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.repos.TaskRepo
import com.ch8n.taskie.data.utils.Result
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepo: TaskRepo) : ViewModel() {

    private val _prompts = MutableLiveData<String>()
    val prompts: LiveData<String> = _prompts

    fun getTask() = taskRepo.getTask()

    fun addTask(todo: Note) {
        viewModelScope.launch {
            val result = taskRepo.upsertTask(todo)
            when (result) {
                is Result.Error -> _prompts.value = result.error.message ?: "Something went wrong!"
                is Result.Success -> _prompts.value = "Task added"
            }
        }
    }

    fun updateTask(task: Note) {
        viewModelScope.launch {
            val result = taskRepo.upsertTask(task)
            when (result) {
                is Result.Error -> _prompts.value = result.error.message ?: "Something went wrong!"
                is Result.Success -> _prompts.value = "Task updated"
            }
        }
    }

    fun deleteTask(task: Note) {
        viewModelScope.launch {
            val result = taskRepo.deleteTask(task.id)
            when (result) {
                is Result.Error -> _prompts.value = result.error.message ?: "Something went wrong!"
                is Result.Success -> _prompts.value = "Task deleted"
            }
        }
    }


}