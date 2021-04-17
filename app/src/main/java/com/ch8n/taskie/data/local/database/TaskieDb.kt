package com.ch8n.taskie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ch8n.taskie.data.local.database.entities.NoteEntity
import com.ch8n.taskie.data.local.database.entities.TodoEntity


@Database(
    entities = [TodoEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskieDB : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun todoDao(): TodoDao
}

const val DB_NAME = "taskie_db"

class AppDB(private val database: TaskieDB) {
    val notesDao: NotesDao by lazy { database.notesDao() }
    val todoDao: TodoDao by lazy { database.todoDao() }
}