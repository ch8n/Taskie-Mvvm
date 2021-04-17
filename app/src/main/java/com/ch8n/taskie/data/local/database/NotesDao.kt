package com.ch8n.taskie.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch8n.taskie.data.local.database.entities.NoteEntity


@Dao
interface NotesDao {

    //######## Add/Update ##########
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(node: NoteEntity)

    //######## Select ##########
    @Query("SELECT * FROM NoteEntity")
    suspend fun getNotes(): LiveData<List<NoteEntity>>

    //######## Delete ##########

    @Query("DELETE FROM NoteEntity")
    suspend fun clearNotes()

    @Query("DELETE FROM NoteEntity WHERE id=:noteId")
    suspend fun deleteNote(noteId: String)

}