package com.ch8n.taskie.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.repos.NotesRepo
import com.ch8n.taskie.data.utils.Result
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepo: NotesRepo) : ViewModel() {

    private val _prompts = MutableLiveData<String>()
     val prompts: LiveData<String> = _prompts

    fun getNotes() = notesRepo.getNotes()

    fun addNote(note: Note) {
        viewModelScope.launch {
            val result = notesRepo.upsertNote(note)
            when (result) {
                is Result.Error -> _prompts.value = result.error.message ?: "Something went wrong!"
                is Result.Success -> _prompts.value = "Note added"
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            val result = notesRepo.upsertNote(note)
            when (result) {
                is Result.Error -> _prompts.value = result.error.message ?: "Something went wrong!"
                is Result.Success -> _prompts.value = "Note updated"
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            val result = notesRepo.deleteNote(note.id)
            when (result) {
                is Result.Error -> _prompts.value = result.error.message ?: "Something went wrong!"
                is Result.Success -> _prompts.value = "Note deleted"
            }
        }
    }


}