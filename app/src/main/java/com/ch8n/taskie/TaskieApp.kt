package com.ch8n.taskie

import android.app.Application
import com.ch8n.taskie.di.Injector

class TaskieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}