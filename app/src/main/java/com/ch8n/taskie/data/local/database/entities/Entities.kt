package com.ch8n.taskie.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long
)

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long
)