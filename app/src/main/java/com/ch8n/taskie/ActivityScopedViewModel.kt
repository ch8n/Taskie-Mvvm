package com.ch8n.taskie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.ui.notes.HomeNoteSharedViewModel
import com.ch8n.taskie.ui.task.HomeTaskSharedViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ActivityScopedViewModel :
    ViewModel(),
    HomeNoteSharedViewModel,
    HomeTaskSharedViewModel {

    fun openCreateNoteDialog() {
        val placeholderNote = Note
            .defaultNote("super awesome work!", description = "tell me more about it?")
        _createNewNote.trySend(placeholderNote)
    }

    private val _createNewNote = Channel<Note>()
    override val createNewNote: ReceiveChannel<Note>
        get() = _createNewNote
}