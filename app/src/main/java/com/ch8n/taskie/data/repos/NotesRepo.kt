package com.ch8n.taskie.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ch8n.taskie.data.local.database.NotesDao
import com.ch8n.taskie.data.local.database.entities.NoteEntity
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepo(private val notesDao: NotesDao) {

    fun getNotes(): LiveData<List<Note>> = Transformations.map(notesDao.getNotes()) { notes ->
        return@map notes.map { it.toNote() }
    }

    suspend fun addNote(note: Note): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        return@withContext Result.build { notesDao.addNote(note.toNoteEntity()) }
    }

    suspend fun deleteNote(noteId: String): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        return@withContext Result.build { notesDao.deleteNote(noteId) }
    }

}

private fun NoteEntity.toNote(): Note {
    return Note(
        title = title,
        description = description,
        createdAt = createdAt,
        id = id,
        type = NoteType.Note
    )
}

private fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt
    )
}