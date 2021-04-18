package com.ch8n.taskie.data.local.prefs

import android.content.Context

const val App_Prefs_Name = "taskie_prefs"
const val KEY_LOGIN = "$App_Prefs_Name.is_login"
const val KEY_FIRST_TIME = "$App_Prefs_Name.is_first_time"
const val KEY_USER_NAME = "$App_Prefs_Name.user_name"
const val KEY_USER_EMAIL = "$App_Prefs_Name.user_email"
const val KEY_SOCIAL_ID = "$App_Prefs_Name.social_id"
const val KEY_AVATAR_URL = "$App_Prefs_Name.avatar_url"

class AppPrefs(appContext: Context) : PrefsConfig(appContext, App_Prefs_Name) {

    var isLogin: Boolean
        get() = prefs.get(KEY_LOGIN, false)
        set(value) = prefs.put(KEY_LOGIN, value)

    var firstTimeLaunch: Boolean
        get() = prefs.get(KEY_FIRST_TIME, true)
        set(value) = prefs.put(KEY_FIRST_TIME, value)

    var userName: String
        get() = prefs.get(KEY_USER_NAME, "Stranger")
        set(value) = prefs.put(KEY_USER_NAME, value)

    var userEmail: String
        get() = prefs.get(KEY_USER_EMAIL, "lost.soul@email.gone")
        set(value) = prefs.put(KEY_USER_EMAIL, value)

    var socialId: String
        get() = prefs.get(KEY_SOCIAL_ID, "")
        set(value) = prefs.put(KEY_SOCIAL_ID, value)

    var avatarUrl: String
        get() = prefs.get(KEY_AVATAR_URL, "")
        set(value) = prefs.put(KEY_AVATAR_URL, value)

}