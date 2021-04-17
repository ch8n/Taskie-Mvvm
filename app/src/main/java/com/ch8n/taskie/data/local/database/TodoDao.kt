package com.ch8n.taskie.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch8n.taskie.data.local.database.entities.TodoEntity


@Dao
interface TodoDao {

    //######## Add/Update ##########
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoEntity)

    //######## Select ##########
    @Query("SELECT * FROM TodoEntity")
    fun getTodos(): LiveData<List<TodoEntity>>

    //######## Delete ##########

    @Query("DELETE FROM TodoEntity")
    suspend fun clearTodos()

    @Query("DELETE FROM TodoEntity WHERE id=:noteId")
    suspend fun deleteTodo(noteId: String)

}