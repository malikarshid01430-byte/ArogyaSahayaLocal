package com.example.arogyasahayalocal.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("arogya_prefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val IS_LOGGED_IN = "is_logged_in"
        const val SELECTED_LANGUAGE = "selected_language"
    }

    fun saveAuthToken(userId: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, userId)
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUserId(): Int = prefs.getInt(USER_ID, -1)

    fun isLoggedIn(): Boolean = prefs.getBoolean(IS_LOGGED_IN, false)

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun setLanguage(lang: String) {
        prefs.edit().putString(SELECTED_LANGUAGE, lang).apply()
    }

    fun getLanguage(): String = prefs.getString(SELECTED_LANGUAGE, "en") ?: "en"
}
