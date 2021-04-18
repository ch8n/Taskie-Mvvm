package com.ch8n.taskie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ch8n.taskie.data.local.database.entities.NoteEntity
import com.ch8n.taskie.data.local.database.entities.TaskEntity


@Database(
    entities = [TaskEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskieDB : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun todoDao(): TaskDao
}

const val DB_NAME = "taskie_db"
