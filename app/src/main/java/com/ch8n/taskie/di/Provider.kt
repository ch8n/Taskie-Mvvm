package com.ch8n.taskie.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ch8n.taskie.TaskieApp
import com.ch8n.taskie.data.local.database.DB_NAME
import com.ch8n.taskie.data.local.database.NotesDao
import com.ch8n.taskie.data.local.database.TaskieDB
import com.ch8n.taskie.data.local.database.TodoDao
import com.ch8n.taskie.data.local.prefs.AppPrefs
import com.ch8n.taskie.data.repos.NotesRepo
import com.ch8n.taskie.data.repos.TodoRepo

// How Dependency Injection working?
// checkout my post explaining it in details : https://chetangupta.net/native-di/
object Provider {

    fun provideRoomDBClient(applicationContext: Context): RoomDatabase.Builder<TaskieDB> =
        Room.databaseBuilder(applicationContext, TaskieDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration()

    fun provideTaskieDB(roomClient: RoomDatabase.Builder<TaskieDB>): TaskieDB =
        roomClient.build()

    fun provideTaskiePrefs(applicationContext: Context): AppPrefs =
        AppPrefs(applicationContext)

    fun provideNotesRepo(notesDao: NotesDao) = NotesRepo(notesDao)

    fun provideTodoRepo(todoDao: TodoDao) = TodoRepo(todoDao)

}