package com.ch8n.taskie.data.local.prefs

import android.content.Context

const val App_Prefs_Name = "taskie_prefs"
const val KEY_LOGIN = "$App_Prefs_Name.is_login"
const val KEY_FIRST_TIME = "$App_Prefs_Name.is_first_time"

class AppPrefs(appContext: Context) : PrefsConfig(appContext, App_Prefs_Name) {

    var isLogin: Boolean
        get() = prefs.get(KEY_LOGIN, false)
        set(value) = prefs.put(KEY_LOGIN, value)

    var firstTimeLaunch: Boolean
        get() = prefs.get(KEY_FIRST_TIME, true)
        set(value) = prefs.put(KEY_FIRST_TIME, value)

}