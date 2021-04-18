package com.ch8n.taskie.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch8n.taskie.data.local.database.entities.TaskEntity


@Dao
interface TaskDao {

    //######## Add/Update ##########
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: TaskEntity)

    //######## Select ##########
    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): LiveData<List<TaskEntity>>

    //######## Delete ##########

    @Query("DELETE FROM TaskEntity")
    suspend fun clearTasks()

    @Query("DELETE FROM TaskEntity WHERE id=:noteId")
    suspend fun deleteTask(noteId: String)

}