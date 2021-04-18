package com.ch8n.taskie.data.repos


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ch8n.taskie.data.local.database.NotesDao
import com.ch8n.taskie.data.local.database.TodoDao
import com.ch8n.taskie.data.local.database.entities.NoteEntity
import com.ch8n.taskie.data.local.database.entities.TodoEntity
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepo(private val todoDao: TodoDao) {

    fun getTodos(): LiveData<List<Note>> = Transformations.map(todoDao.getTodos()) { todos ->
        return@map todos.map { it.toTodo() }.sortedByDescending { it.createdAt }
    }

    suspend fun addTodo(note: Note): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        return@withContext Result.build { todoDao.addTodo(note.toTodoEntity()) }
    }

    suspend fun deleteTodo(noteId: String): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        return@withContext Result.build { todoDao.deleteTodo(noteId) }
    }

}

private fun TodoEntity.toTodo(): Note {
    return Note(
        title = title,
        description = description,
        createdAt = createdAt,
        id = id,
        type = NoteType.Todo(isCompleted)
    )
}

private fun Note.toTodoEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        isCompleted = (type as? NoteType.Todo)?.isCompleted == true
    )
}