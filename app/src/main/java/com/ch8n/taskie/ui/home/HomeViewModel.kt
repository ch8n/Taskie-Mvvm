package com.ch8n.taskie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.taskie.data.local.prefs.AppPrefs
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.repos.NotesRepo
import com.ch8n.taskie.data.repos.TaskRepo
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.notes.HomeNoteSharedViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val taskiePrefs: AppPrefs,
    private val taskRepo: TaskRepo,
    private val notesRepo: NotesRepo
) : ViewModel() {

    fun oneTimeSetup(){
        if (taskiePrefs.firstTimeLaunch) {
            viewModelScope.launch {
                taskRepo.upsertTask(
                    Note.defaultTask(
                        title = "Welcome to Taskie... by Chetan Gupta!",
                        description = "Long Press for more option \nClick to toggle status\nClick `+` to add a task"
                    )
                )

                notesRepo.upsertNote(
                    Note.defaultNote(
                        title = "Welcome to Taskie... by Chetan Gupta!",
                        description = "Long Press for more option \nClick `+` to add new note"
                    )
                )
            }
            taskiePrefs.firstTimeLaunch = false
        }
    }

}