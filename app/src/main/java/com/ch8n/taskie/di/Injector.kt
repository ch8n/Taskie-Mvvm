package com.ch8n.taskie.di

import android.content.Context


// How Dependency Injection working?
// checkout my post explaining it in details : https://chetangupta.net/native-di/
object Injector {

    lateinit var appContext: Context

    fun init(appContext: Context) {
        this.appContext = appContext
    }

    private val roomClient by lazy { Provider.provideRoomDBClient(appContext) }
    private val taskieDB by lazy { Provider.provideTaskieDB(roomClient) }

    val taskiePrefs by lazy { Provider.provideTaskiePrefs(appContext) }

    val notesRepo by lazy { Provider.provideNotesRepo(taskieDB.notesDao()) }

    val todoRepo by lazy { Provider.provideTodoRepo(taskieDB.todoDao()) }

    val noteVM by lazy { Provider.provideNoteVM(notesRepo) }

}