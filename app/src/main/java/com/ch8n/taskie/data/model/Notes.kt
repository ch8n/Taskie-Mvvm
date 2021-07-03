package com.ch8n.taskie.data.model

import android.os.Parcelable
import com.github.javafaker.Faker
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class NoteType : Parcelable {
    @Parcelize
    object Note : NoteType()
    @Parcelize
    object Todo : NoteType()
}

@Parcelize
data class Note(
    val id: String,
    val title: String,
    val description: String,
    val type: NoteType,
    val createdAt: Long,
    val isCompleted: Boolean = false
): Parcelable {
    companion object {
        fun defaultNote(
            title: String = "",
            description: String = "",
            type: NoteType = NoteType.Note,
            createdAt: Long = Calendar.getInstance().timeInMillis,
            isCompleted: Boolean = false
        ) = Note(UUID.randomUUID().toString(), title, description, type, createdAt, isCompleted)

        fun defaultTask(
            title: String = "",
            description: String = "",
            type: NoteType = NoteType.Todo,
            createdAt: Long = Calendar.getInstance().timeInMillis,
            isCompleted: Boolean = false
        ) = Note(UUID.randomUUID().toString(), title, description, type, createdAt, isCompleted)

        fun fakeNote() = with(Faker.instance()) {
            defaultNote(
                title = rickAndMorty().location(),
                description = rickAndMorty().quote()
            )
        }

        fun fakeTodo() = with(Faker.instance()) {
            defaultTask(
                title = dragonBall().character(),
                description = this.lorem().sentence()
            )
        }
    }
}
