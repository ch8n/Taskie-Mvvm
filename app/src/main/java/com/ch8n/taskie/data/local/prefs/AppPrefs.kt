package com.ch8n.taskie.data.local.prefs

import android.content.Context

const val App_Prefs_Name = "taskie_prefs"
const val KEY_LOGIN = "$App_Prefs_Name.is_login"

class AppPrefs(appContext: Context) : PrefsConfig(appContext, App_Prefs_Name) {

    var isLogin: Boolean
        get() = prefs.get(KEY_LOGIN, false)
        set(value) = prefs.put(KEY_LOGIN, value)

}