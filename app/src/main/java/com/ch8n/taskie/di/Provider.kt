package com.ch8n.taskie.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ch8n.taskie.data.local.database.DB_NAME
import com.ch8n.taskie.data.local.database.NotesDao
import com.ch8n.taskie.data.local.database.TaskieDB
import com.ch8n.taskie.data.local.database.TaskDao
import com.ch8n.taskie.data.local.prefs.AppPrefs
import com.ch8n.taskie.data.repos.NotesRepo
import com.ch8n.taskie.data.repos.TaskRepo
import com.ch8n.taskie.ui.home.HomeViewModel
import com.ch8n.taskie.ui.home.HomeViewModelFactory
import com.ch8n.taskie.ui.notes.NotesViewModel
import com.ch8n.taskie.ui.notes.NotesViewModelFactory
import com.ch8n.taskie.ui.task.TaskViewModel
import com.ch8n.taskie.ui.task.TaskViewModelFactory

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

    fun provideTodoRepo(taskDao: TaskDao) = TaskRepo(taskDao)

    fun provideNoteViewModelFactory(noteRepo: NotesRepo) = NotesViewModelFactory(noteRepo)

    fun provideTaskViewModelFactory(taskRepo: TaskRepo) = TaskViewModelFactory(taskRepo)

    fun provideHomeViewModelFactory(
        taskiePrefs: AppPrefs,
        taskRepo: TaskRepo,
        notesRepo: NotesRepo
    ) = HomeViewModelFactory(taskiePrefs, taskRepo, notesRepo)

}