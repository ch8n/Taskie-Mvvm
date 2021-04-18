package com.ch8n.taskie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.taskie.data.local.prefs.AppPrefs
import com.ch8n.taskie.data.repos.NotesRepo
import com.ch8n.taskie.data.repos.TaskRepo


class HomeViewModelFactory(
    private val taskiePrefs: AppPrefs,
    private val taskRepo: TaskRepo,
    private val notesRepo: NotesRepo
):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(taskiePrefs, taskRepo, notesRepo) as T
    }
}