package com.ch8n.taskie.data.model

import com.github.javafaker.Faker
import java.util.*

sealed class NoteType {
    object Note : NoteType()
    data class Todo(val isCompleted: Boolean) : NoteType()
}

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val type: NoteType,
    val createdAt: Long
) {
    companion object {
        fun defaultNote(
            title: String = "",
            description: String = "",
            type: NoteType = NoteType.Note,
            createdAt: Long = Calendar.getInstance().timeInMillis
        ) = Note(UUID.randomUUID().toString(), title, description, type, createdAt)

        fun defaultTodo(
            title: String = "",
            description: String = "",
            type: NoteType = NoteType.Todo(isCompleted = false),
            createdAt: Long = Calendar.getInstance().timeInMillis
        ) = Note(UUID.randomUUID().toString(), title, description, type, createdAt)

        fun fakeNote() = with(Faker.instance()) {
            defaultNote(
                title = rickAndMorty().location(),
                description = rickAndMorty().quote()
            )
        }

        fun fakeTodo() = with(Faker.instance()) {
            defaultNote(
                title = dragonBall().character(),
                description = this.lorem().sentence()
            )
        }


    }
}
