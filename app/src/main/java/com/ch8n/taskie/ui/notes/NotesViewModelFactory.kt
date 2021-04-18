package com.ch8n.taskie.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.taskie.data.repos.NotesRepo

class NotesViewModelFactory(
    private val notesRepo: NotesRepo
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesViewModel(notesRepo) as T
    }
}