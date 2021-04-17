package com.ch8n.taskie.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

abstract class PrefsConfig constructor(context: Context, prefFile:String) {

    protected var prefs: SharedPreferences = context.applicationContext.getSharedPreferences(prefFile,
        Context.MODE_PRIVATE)
        private set

}