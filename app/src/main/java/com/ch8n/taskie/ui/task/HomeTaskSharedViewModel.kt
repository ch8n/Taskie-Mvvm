package com.ch8n.taskie.ui.task

import com.ch8n.taskie.data.model.Note
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.StateFlow

interface HomeTaskSharedViewModel {
    val createNewNote: ReceiveChannel<Note>
}