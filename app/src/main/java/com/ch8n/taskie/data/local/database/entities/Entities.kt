package com.ch8n.taskie.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long
)

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long
)