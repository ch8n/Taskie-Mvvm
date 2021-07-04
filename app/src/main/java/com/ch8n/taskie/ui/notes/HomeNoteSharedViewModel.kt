package com.ch8n.taskie.ui.notes


import com.ch8n.taskie.data.model.Note
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.StateFlow

interface HomeNoteSharedViewModel {
    val createNewNote: ReceiveChannel<Note>
}