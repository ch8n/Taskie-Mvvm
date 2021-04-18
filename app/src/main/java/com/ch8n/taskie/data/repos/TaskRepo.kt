package com.ch8n.taskie.data.repos


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ch8n.taskie.data.local.database.TaskDao
import com.ch8n.taskie.data.local.database.entities.TaskEntity
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepo(private val taskDao: TaskDao) {

    fun getTask(): LiveData<List<Note>> = Transformations.map(taskDao.getTasks()) { todos ->
        return@map todos.map { it.toTask() }.sortedByDescending { it.createdAt }
    }

    suspend fun upsertTask(note: Note): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        return@withContext Result.build { taskDao.upsertTask(note.toTaskEntity()) }
    }

    suspend fun deleteTask(noteId: String): Result<Unit, Exception> = withContext(Dispatchers.IO) {
        return@withContext Result.build { taskDao.deleteTask(noteId) }
    }

}

private fun TaskEntity.toTask(): Note {
    return Note(
        title = title,
        description = description,
        createdAt = createdAt,
        id = id,
        type = NoteType.Todo,
        isCompleted = isCompleted
    )
}

private fun Note.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        isCompleted = isCompleted
    )
}